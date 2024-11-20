package com.bho.catchtrippingbackend.board.service;

import com.amazonaws.services.s3.AmazonS3;
import com.bho.catchtrippingbackend.board.dao.BoardDao;
import com.bho.catchtrippingbackend.board.dao.BoardImageDao;
import com.bho.catchtrippingbackend.board.dao.BoardLikeDao;
import com.bho.catchtrippingbackend.board.dto.*;
import com.bho.catchtrippingbackend.board.entity.Board;
import com.bho.catchtrippingbackend.board.entity.BoardLike;
import com.bho.catchtrippingbackend.error.SystemException;
import com.bho.catchtrippingbackend.error.code.ClientErrorCode;
import com.bho.catchtrippingbackend.error.code.ServerErrorCode;
import com.bho.catchtrippingbackend.user.dao.UserDao;
import com.bho.catchtrippingbackend.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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

    private final AmazonS3 amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    @Transactional
    public BoardDetailDto save(Long userId, BoardSaveRequestDto requestDto) {
        if (requestDto.imageKeys().isEmpty()) {
            throw new SystemException(ClientErrorCode.INVALID_IMAGE_COUNT);
        }

        User user = getUserById(userId);
        Board board = Board.builder()
                .user(user)
                .content(requestDto.content())
                .build();
        boardDao.save(board);

        // 이미지 키 저장
        requestDto.imageKeys().forEach(key -> boardImageDao.save(board.getId(), key));

        log.info("게시글 저장 완료: Board ID = {}", board.getId());
        return BoardDetailDto.from(board);

//        log.info("유저 이름 : {}", userId);
//        User user = getUserById(userId);
//
//        saveBoard(user, requestDto);
    }

    @Transactional(readOnly = true)
    public BoardDetailDto findBoardDetailById(Long boardId) {
        log.info("Fetching board with ID: {}", boardId);
        Board board = getBoardById(boardId);

        return BoardDetailDto.from(board);
    }

    @Transactional
    public BoardDetailDto update(Long userId, Long boardId, BoardUpdateRequestDto requestDto) {
        Board board = getBoardById(boardId);

        validateBoardAuthor(userId, board);

        board.update(requestDto.content());
        boardDao.update(board);

        return BoardDetailDto.from(board);
    }

    @Transactional
    public void delete(Long userId, Long boardId) {
        Board board = getBoardById(boardId);

        validateBoardAuthor(userId, board);

        // 이미지 삭제
        List<String> imageKeys = boardImageDao.findKeysByBoardId(boardId);
        imageKeys.forEach(this::deleteImageFromS3);

        boardDao.delete(boardId);
        log.info("게시글 삭제 완료: Board ID = {}", boardId);
    }

    @Transactional(readOnly = true)
    public List<BoardPreviewDto> findBoardsWithPaging(int page, int size) {
        int offset = (page - 1) * size;
        List<Board> boards = boardDao.findBoardsWithPaging(offset, size);
        return boards.stream()
                .map(BoardPreviewDto::from)
                .collect(Collectors.toList());
    }

    private void deleteImageFromS3(String key) {
        try {
            amazonS3Client.deleteObject(bucketName, key);
        } catch (Exception e) {
            log.error("Failed to delete image from S3: {}", key, e);
            throw new SystemException(ServerErrorCode.IMAGE_DELETE_FAILED);
        }
    }

    @Transactional
    public void addLike(Long userId, BoardLikeRequestDto requestDto) {
        User user = getUserById(userId);
        Board board = getBoardById(requestDto.boardId());

        validateBoardLikeDuplication(user.getUserId(), board.getId());

        BoardLike boardLike = requestDto.from(user, board);

        boardLikeDao.save(boardLike);
    }

    @Transactional
    public void deleteLike(Long userId, BoardLikeRequestDto requestDto) {
        User user = getUserById(userId);
        Board board = getBoardById(requestDto.boardId());

        validateBoardLikeExistence(user.getUserId(), board.getId());

        boardLikeDao.deleteByUserIdAndBoardId(user.getUserId(), board.getId());
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
        boolean isAuthorized = userId == board.getUser().getUserId();
        if (!isAuthorized) {
            throw new SystemException(ClientErrorCode.FORBIDDEN_USER_ACCESS);
        }
    }

    private Board getBoardById(Long boardId) {
        Board board = boardDao.findBoardById(boardId);
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

    private void saveBoard(User user, BoardSaveRequestDto requestDto) {
        log.info("Saving board with content : {} for user with ID: {}", requestDto.content(), user.getUserName());
        Board board = requestDto.from(user);
        int result = boardDao.save(board);
        log.info("board 저장 잘됐으면 1 반환 : {}", result);
        if (result != 1) {
            throw new SystemException(ServerErrorCode.DATABASE_ERROR);
        }
    }
}