package com.bho.catchtrippingbackend.board.dao;

import com.bho.catchtrippingbackend.board.entity.Board;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MyBoardDao {
    List<Board> findMyBoardsWithPaging(@Param("userId") Long userId, @Param("offset") int offset, @Param("size") int size);
}
