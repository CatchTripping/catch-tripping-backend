package com.bho.catchtrippingbackend.board.service;

import com.bho.catchtrippingbackend.board.dao.BoardDao;
import com.bho.catchtrippingbackend.board.dao.BoardLikeDao;
import com.bho.catchtrippingbackend.board.dto.BoardDetailDto;
import com.bho.catchtrippingbackend.board.dto.BoardLikeRequestDto;
import com.bho.catchtrippingbackend.board.dto.BoardSaveRequestDto;
import com.bho.catchtrippingbackend.board.dto.BoardUpdateRequestDto;
import com.bho.catchtrippingbackend.board.entity.Board;
import com.bho.catchtrippingbackend.board.entity.BoardLike;
import com.bho.catchtrippingbackend.error.SystemException;
import com.bho.catchtrippingbackend.error.code.ClientErrorCode;
import com.bho.catchtrippingbackend.error.code.ServerErrorCode;
import com.bho.catchtrippingbackend.user.dao.UserDao;
import com.bho.catchtrippingbackend.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

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
        boolean isAuthorized = Objects.equals(userId, board.getUser().getUserId());
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