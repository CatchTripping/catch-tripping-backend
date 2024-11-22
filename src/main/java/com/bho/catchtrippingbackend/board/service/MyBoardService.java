package com.bho.catchtrippingbackend.board.service;

import com.bho.catchtrippingbackend.board.dao.MyBoardDao;
import com.bho.catchtrippingbackend.board.dto.BoardDetailDto;
import com.bho.catchtrippingbackend.board.entity.Board;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class MyBoardService {

    private final MyBoardDao myBoardDao;

    @Transactional(readOnly = true)
    public List<BoardDetailDto> findMyBoardsWithPaging(Long userId, int page, int size) {
        int offset = (page - 1) * size;
        List<Board> boards = myBoardDao.findMyBoardsWithPaging(userId, offset, size);

        return boards.stream()
                .map(board -> BoardDetailDto.from(board, board.getImageUrls()))
                .collect(Collectors.toList());    }

}
