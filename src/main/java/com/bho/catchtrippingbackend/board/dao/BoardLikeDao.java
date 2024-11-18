package com.bho.catchtrippingbackend.board.dao;

import com.bho.catchtrippingbackend.board.entity.BoardLike;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardLikeDao {
    int findBoardLikeByBoardIdAndUserId(Long userId, Long boardId);
    int save(BoardLike boardLike);
    int deleteByUserIdAndBoardId(Long userId, Long boardId);
}
