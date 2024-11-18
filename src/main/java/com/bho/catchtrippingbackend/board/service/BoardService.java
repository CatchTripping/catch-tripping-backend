package com.bho.catchtrippingbackend.board.service;

import com.bho.catchtrippingbackend.board.dao.BoardDao;
import com.bho.catchtrippingbackend.board.dao.BoardLikeDao;
import com.bho.catchtrippingbackend.board.dto.BoardDetailDto;
import com.bho.catchtrippingbackend.board.dto.BoardLikeRequestDto;
import com.bho.catchtrippingbackend.board.dto.BoardSaveRequestDto;
import com.bho.catchtrippingbackend.board.dto.BoardUpdateRequestDto;
import com.bho.catchtrippingbackend.board.entity.Board;
import com.bho.catchtrippingbackend.board.entity.BoardLike;
import com.bho.catchtrippingbackend.security.CustomUserDetails;
import com.bho.catchtrippingbackend.user.dao.UserDao;
import com.bho.catchtrippingbackend.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardDao boardDao;
    private final UserDao userDao;
    private final BoardLikeDao boardLikeDao;

    @Transactional
    public void save(Long userId, BoardSaveRequestDto requestDto) {
        log.info("유저 이름 : {}", userId);
        User user = getUserById(userId);

        saveBoard(user, requestDto);
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

        boardDao.delete(boardId);
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

    private void validateBoardLikeExistence(Long userId, Long boardId) {
        int result = boardLikeDao.findBoardLikeByBoardIdAndUserId(userId, boardId);

        if (result == 0) {
            throw new RuntimeException("좋아요가 존재하지 않습니다.");
        }
    }

    private void validateBoardLikeDuplication(Long userId, Long boardId) {
        int result = boardLikeDao.findBoardLikeByBoardIdAndUserId(userId, boardId);

        if (result > 0) {
            throw new RuntimeException("boardLike 중복");
        }
    }

    private void validateBoardAuthor(Long userId, Board board) {
        boolean isAuthorized = userId == board.getUser().getUserId();
        if (!isAuthorized) {
            throw new RuntimeException("권한 없음");
        }
    }

    private Board getBoardById(Long boardId) {
        Board board = boardDao.findBoardById(boardId);
        if (board == null) {
            log.error("Board not found with ID: {}", boardId);
            throw new RuntimeException("Board not found with ID: " + boardId);
        }
        log.info("Board found with ID: {}", boardId);
        return board;
    }

    private User getUserById(Long userId) {

        log.info("Fetching user with userId: {}", userId);
        User user = userDao.findUserById(userId);
        if (user == null) {
            log.error("User not found with name: {}", userId);
            // exception 추후 수정
            throw new RuntimeException("User not found with userId: " + userId);
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
            throw new RuntimeException("Saving board failed");
        }
    }
}