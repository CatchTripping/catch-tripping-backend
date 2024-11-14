package com.bho.catchtrippingbackend.board.service;

import com.bho.catchtrippingbackend.board.dao.BoardDao;
import com.bho.catchtrippingbackend.board.dao.BoardLikeDao;
import com.bho.catchtrippingbackend.board.dto.BoardDetailDto;
import com.bho.catchtrippingbackend.board.dto.BoardLikeRequestDto;
import com.bho.catchtrippingbackend.board.dto.BoardSaveRequestDto;
import com.bho.catchtrippingbackend.board.dto.BoardUpdateRequestDto;
import com.bho.catchtrippingbackend.board.entity.Board;
import com.bho.catchtrippingbackend.board.entity.BoardLike;
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
    public void save(UserDetails userDetails, BoardSaveRequestDto requestDto) {
        log.info("유저 이름 : {}", userDetails.getUsername());
        User user = getUserByName(userDetails.getUsername());

        saveBoard(requestDto, user);
    }

    @Transactional(readOnly = true)
    public BoardDetailDto findBoardDetailById(Long boardId) {
        log.info("Fetching board with ID: {}", boardId);
        Board board = getBoardById(boardId);

        return BoardDetailDto.from(board);
    }

    @Transactional
    public BoardDetailDto update(UserDetails userDetails, Long boardId, BoardUpdateRequestDto requestDto) {
        Board board = getBoardById(boardId);

        validateBoardAuthor(userDetails, board);

        board.update(requestDto.content());
        boardDao.update(board);

        return BoardDetailDto.from(board);
    }

    @Transactional
    public void delete(UserDetails userDetails, Long boardId) {
        Board board = getBoardById(boardId);
        validateBoardAuthor(userDetails, board);
        boardDao.delete(boardId);
    }

    @Transactional
    public void addLike(UserDetails userDetails, BoardLikeRequestDto requestDto) {
        // userDetail에 id 추가되면 validate 코드 수정
        User user = getUserByName(userDetails.getUsername());
        Board board = getBoardById(requestDto.boardId());

        validateBoardLikeDuplication(user, requestDto.boardId());

        BoardLike boardLike = requestDto.from(user, board);

        boardLikeDao.save(boardLike);
    }

    private void validateBoardLikeDuplication(User user, Long boardId) {
        int result = boardLikeDao.findBoardLikeByBoardIdAndUserId(user.getUserId(), boardId);

        if (result > 0) {
            throw new RuntimeException("boardLike 중복");
        }
    }

    private void validateBoardAuthor(UserDetails userDetails, Board board) {
        boolean isAuthorized = userDetails.getUsername().equals(board.getUser().getUserName());

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


    private User getUserByName(String name) {
        log.info("Fetching user with name: {}", name);
        User user = userDao.findUserByUsername(name);
        if (user == null) {
            log.error("User not found with name: {}", name);
            // exception 추후 수정
            throw new RuntimeException("User not found with name: " + name);
        }
        log.info("name으로 유저 db에서 조회 후 반환 : {}", user.getUserId());
        return user;
    }


    private void saveBoard(BoardSaveRequestDto requestDto, User user) {
        log.info("Saving board with content : {} for user with ID: {}", requestDto.content(), user.getUserName());
        Board board = requestDto.from(user);
        int result = boardDao.save(board);
        log.info("board 저장 잘됐으면 1 반환 : {}", result);
        if (result != 1) {
            throw new RuntimeException("Saving board failed");
        }
    }
}