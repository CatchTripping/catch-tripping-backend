package com.bho.catchtrippingbackend.board.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BoardServiceTest {

    @Autowired
    BoardService boardService;

    @Test
    void test() {
        boardService.validateBoardLikeExistence(1L, 1L);
    }

}