package com.bho.catchtrippingbackend.board.service;

import com.bho.catchtrippingbackend.board.dao.BoardDao;
import com.bho.catchtrippingbackend.board.dto.BoardDetailDTO;
import com.bho.catchtrippingbackend.board.dto.BoardSaveRequestDto;
import com.bho.catchtrippingbackend.board.entity.Board;
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

    @Transactional
    public void save(UserDetails userDetails, BoardSaveRequestDto requestDTO) {
        log.info("유저 이름 : {}", userDetails.getUsername());
        User user = getUserByName(userDetails.getUsername());
        Board board =  saveBoard(requestDTO, user);
        log.info("board 저장 완료 with id: {}", board.getId());
    }

    @Transactional(readOnly = true)
    public BoardDetailDTO getBoardDetailById(Long boardId) {
        log.info("Fetching board with ID: {}", boardId);
        Board board = getBoardById(boardId);

        return BoardDetailDTO.from(board);
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


    private Board saveBoard(BoardSaveRequestDto requestDto, User user) {
        log.info("Saving board with content : {} for user with ID: {}", requestDto.content(), user.getUserName());
        Board board = requestDto.from(user);
        int result = boardDao.saveBoard(board);
        log.info("board 저장 잘됐으면 1 반환 : {}", result);
        if (result != 1) {
            throw new RuntimeException("Saving board failed");
        }

        return board;
    }
}