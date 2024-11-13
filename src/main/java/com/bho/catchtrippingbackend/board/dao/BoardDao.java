package com.bho.catchtrippingbackend.board.dao;

import com.bho.catchtrippingbackend.board.entity.Board;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardDao {
    int saveBoard(Board board);
    int updateBoard(Board board);
    int deleteBoard(Long boardId);
    int selectBoard(Board board);
}