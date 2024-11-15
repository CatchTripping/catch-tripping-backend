package com.bho.catchtrippingbackend.attractions.dao;

import com.bho.catchtrippingbackend.attractions.dto.ContentDetails;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ContentDetailsDao {

    // content ID로 콘텐츠 상세 정보 조회
    ContentDetails findByContentId(@Param("contentId") int contentId);

    // 새로운 콘텐츠 상세 정보 삽입
    void insertContentDetails(ContentDetails contentDetails);
}
