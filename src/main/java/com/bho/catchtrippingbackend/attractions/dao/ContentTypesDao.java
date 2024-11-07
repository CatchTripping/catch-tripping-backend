package com.bho.catchtrippingbackend.attractions.dao;

import com.bho.catchtrippingbackend.attractions.dto.ContentTypes;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ContentTypesDao {

    // Primary Key로 조회
    ContentTypes findByContentTypeId(int contentTypeId);

    // 모든 콘텐츠 유형 조회
    List<ContentTypes> findAll();

    // 특정 콘텐츠 유형 이름으로 조회 (정확히 일치하는 경우)
    List<ContentTypes> findByContentTypeName(String contentTypeName);

    // 특정 이름을 포함하는 콘텐츠 유형 조회 (부분 일치 검색)
    List<ContentTypes> findByContentTypeNameContaining(String partialName);
}
