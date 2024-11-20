package com.bho.catchtrippingbackend.board.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BoardImageDao {
    // 게시글 ID로 모든 이미지 키 검색
    List<String> findKeysByBoardId(@Param("boardId") Long boardId);

    // 이미지 키 저장
    void save(@Param("boardId") Long boardId, @Param("imageKey") String imageKey);

    // 이미지 키 삭제
    void deleteByKey(@Param("imageKey") String imageKey);

    // 게시글 ID로 연결된 모든 이미지 삭제
    void deleteByBoardId(@Param("boardId") Long boardId);
}
