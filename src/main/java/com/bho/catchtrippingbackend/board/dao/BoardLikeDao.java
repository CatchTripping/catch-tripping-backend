package com.bho.catchtrippingbackend.board.dao;

import com.bho.catchtrippingbackend.board.entity.BoardLike;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardLikeDao {
    int findBoardLikeByBoardIdAndUserId(int userId, Long boardId);
    int save(BoardLike boardLike);
    int deleteByUserIdAndBoardId(int userId, Long boardId);
}
