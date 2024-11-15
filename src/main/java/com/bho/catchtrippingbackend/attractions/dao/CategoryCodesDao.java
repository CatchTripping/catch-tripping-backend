package com.bho.catchtrippingbackend.attractions.dao;

import com.bho.catchtrippingbackend.attractions.dto.CategoryCodes;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CategoryCodesDao {

    // Primary Key로 조회
    CategoryCodes findByCategoryCode(String categoryCode);

    // 모든 카테고리 코드 조회
    List<CategoryCodes> findAll();

    // 특정 카테고리 이름으로 조회 (정확히 일치하는 경우)
    List<CategoryCodes> findByCategoryName(String categoryName);

    // 특정 이름을 포함하는 카테고리 코드 조회 (부분 일치 검색)
    List<CategoryCodes> findByCategoryNameContaining(String partialName);
}
