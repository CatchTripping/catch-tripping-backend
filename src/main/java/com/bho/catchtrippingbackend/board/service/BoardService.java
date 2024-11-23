package com.bho.catchtrippingbackend.board.service;

import com.amazonaws.HttpMethod;
import com.bho.catchtrippingbackend.board.dao.BoardDao;
import com.bho.catchtrippingbackend.board.dao.BoardImageDao;
import com.bho.catchtrippingbackend.board.dao.BoardLikeDao;
import com.bho.catchtrippingbackend.board.dto.*;
import com.bho.catchtrippingbackend.board.entity.Board;
import com.bho.catchtrippingbackend.board.entity.BoardLike;
import com.bho.catchtrippingbackend.error.SystemException;
import com.bho.catchtrippingbackend.error.code.ClientErrorCode;
import com.bho.catchtrippingbackend.error.code.ServerErrorCode;
import com.bho.catchtrippingbackend.s3.service.S3Service;
import com.bho.catchtrippingbackend.user.dao.UserDao;
import com.bho.catchtrippingbackend.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardDao boardDao;
    private final UserDao userDao;
    private final BoardLikeDao boardLikeDao;
    private final BoardImageDao boardImageDao;
    private final S3Service s3Service;

    @Transactional
    public void save(Long userId, BoardSaveRequestDto requestDto) {
        User user = getUserById(userId);
        validateImageKeys(requestDto.imageKeys());

        Board board = saveBoard(user, requestDto);

        moveImagesAndSaveKeys(board.getId(), requestDto.imageKeys());

        log.info("게시글 저장 및 이미지 이동 완료: Board ID = {}", board.getId());
    }

    private void validateImageKeys(List<String> imageKeys) {
        if (imageKeys == null || imageKeys.isEmpty()) {
            throw new SystemException(ClientErrorCode.INVALID_IMAGE_COUNT);
        }
    }

    private void moveImagesAndSaveKeys(Long boardId, List<String> tempImageKeys) {
        List<String> updatedImageKeys = tempImageKeys.stream().map(tempKey -> {
            String newKey = tempKey.replace("board-images/temp", "board-images/" + boardId);
            s3Service.moveObject(tempKey, newKey);
            return newKey;
        }).toList();

        updatedImageKeys.forEach(key -> boardImageDao.save(boardId, key));
    }

    @Transactional(readOnly = true)
    public BoardDetailDto findBoardDetailById(Long userId, Long boardId) {
        log.info("Fetching board with ID: {}", boardId);
        Board board = getBoardById(userId, boardId);

        String profileImage = s3Service.generatePresignedUrl(board.getUser().getProfileImage(), HttpMethod.GET);

        List<String> imageKeys = boardImageDao.findKeysByBoardId(boardId);

        // 키를 Presigned URL로 반환
        List<String> imageUrls = generateImageUrls(imageKeys);

        return BoardDetailDto.from(board, profileImage, imageUrls);
    }

    @Transactional
    public BoardDetailDto update(Long userId, Long boardId, BoardUpdateRequestDto requestDto) {
        Board board = getBoardById(userId, boardId);

        validateBoardAuthor(userId, board);

        board.update(requestDto.content());
        boardDao.update(board);

        String profileImage = s3Service.generatePresignedUrl(board.getUser().getProfileImage(), HttpMethod.GET);

        // 기존 이미지 키 가져오기
        List<String> imageKeys = boardImageDao.findKeysByBoardId(boardId);

        // 키를 Presigned GET URL로 변환
        List<String> imageUrls = generateImageUrls(imageKeys);

        return BoardDetailDto.from(board, profileImage, imageUrls);
    }

    @Transactional
    public void delete(Long userId, Long boardId) {
        Board board = getBoardById(userId, boardId);

        validateBoardAuthor(userId, board);

        // 1. S3에서 해당 게시물 폴더 삭제
        String folderPath = "board-images/" + boardId + "/";
        s3Service.deleteFolder(folderPath);

        // 2. board_image 테이블에서 해당 레코드 삭제
        boardImageDao.deleteByBoardId(boardId);

        // 3. board 테이블에서 게시물 삭제
        int result = boardDao.delete(boardId);
        if (result != 1) {
            throw new SystemException(ServerErrorCode.DATABASE_ERROR);
        }
        log.info("게시글 삭제 완료: Board ID = {}", boardId);
    }

    @Transactional(readOnly = true)
    public List<BoardDetailDto> findBoardsWithPaging(Long userId, int page, int size) {
        int offset = (page - 1) * size;
        List<Board> boards = boardDao.findBoardsWithPaging(userId, offset, size);

        return boards.stream()
                .map(board -> {
                    String profileImage = s3Service.generatePresignedUrl(board.getUser().getProfileImage(), HttpMethod.GET);
                    List<String> imageKeys = boardImageDao.findKeysByBoardId(board.getId());
                    List<String> imageUrls = generateImageUrls(imageKeys);
                    return BoardDetailDto.from(board, profileImage, imageUrls);
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public void addLike(Long userId, BoardLikeRequestDto requestDto) {
        User user = getUserById(userId);
        Board board = getBoardById(userId, requestDto.boardId());

        validateBoardLikeDuplication(user.getUserId(), board.getId());

        BoardLike boardLike = requestDto.toEntity(user, board);

        board.incrementLikeCount();
        boardDao.updateLike(board);

        boardLikeDao.save(boardLike);
    }

    @Transactional
    public void deleteLike(Long userId, BoardLikeRequestDto requestDto) {
        User user = getUserById(userId);
        Board board = getBoardById(userId, requestDto.boardId());

        validateBoardLikeExistence(user.getUserId(), board.getId());

        boardLikeDao.deleteByUserIdAndBoardId(user.getUserId(), board.getId());

        board.decrementLikeCount();
        boardDao.updateLike(board);
    }

    public void validateBoardLikeExistence(Long userId, Long boardId) {
        int result = boardLikeDao.findBoardLikeByBoardIdAndUserId(userId, boardId);

        if (result == 0) {
            throw new SystemException(ClientErrorCode.BOARD_LIKE_NOT_FOUND);
        }
    }

    private void validateBoardLikeDuplication(Long userId, Long boardId) {
        int result = boardLikeDao.findBoardLikeByBoardIdAndUserId(userId, boardId);

        if (result > 0) {
            throw new SystemException(ClientErrorCode.BOARD_LIKE_DUPLICATED);
        }
    }

    private void validateBoardAuthor(Long userId, Board board) {
        if (userId != board.getUser().getUserId()) {
            throw new SystemException(ClientErrorCode.FORBIDDEN_USER_ACCESS);
        }
    }

    private Board getBoardById(Long userId, Long boardId) {
        Board board = boardDao.findBoardById(userId, boardId);
        if (board == null) {
            log.error("Board not found with ID: {}", boardId);
            throw new SystemException(ClientErrorCode.BOARD_NOT_FOUND);
        }
        log.info("Board found with ID: {}", boardId);
        return board;
    }

    private User getUserById(Long userId) {

        log.info("Fetching user with userId: {}", userId);
        User user = userDao.findUserById(userId);
        if (user == null) {
            log.error("User not found with name: {}", userId);
            throw new SystemException(ClientErrorCode.USER_NOT_FOUND);
        }
        log.info("userId으로 유저 db에서 조회 후 반환 : {}", user.getUserName());
        return user;
    }

    private Board saveBoard(User user, BoardSaveRequestDto requestDto) {
        log.info("Saving board with content : {} for user with ID: {}", requestDto.content(), user.getUserName());
        Board board = requestDto.toEntity(user);
        boardDao.save(board);
        log.info("board 저장 : {}", board.getId());
        if (board.getId() == null) {
            throw new SystemException(ServerErrorCode.DATABASE_ERROR);
        }

        return board;
    }

    private List<String> generateImageUrls(List<String> keys) {
        return keys.stream()
                .map(key -> s3Service.generatePresignedUrl(key, HttpMethod.GET))
                .collect(Collectors.toList());
    }
}