package com.bho.catchtrippingbackend.board.dao;

import com.bho.catchtrippingbackend.board.entity.Board;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BoardDao {
    int save(Board board);
    int update(Board board);
    int delete(Long boardId);
    Board findBoardById(@Param("userId") Long userId, @Param("boardId") Long boardId);
    List<Board> findBoardsWithPaging(@Param("userId") Long userId, @Param("offset") int offset, @Param("size") int size);
    int updateLike(Board board);
}