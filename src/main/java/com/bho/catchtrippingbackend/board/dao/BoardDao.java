package com.bho.catchtrippingbackend.board.dao;

import com.bho.catchtrippingbackend.board.entity.Board;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BoardDao {
    int save(Board board);
    int update(Board board);
    int delete(Long boardId);
    Board findBoardById(@Param("boardId") Long boardId);
}