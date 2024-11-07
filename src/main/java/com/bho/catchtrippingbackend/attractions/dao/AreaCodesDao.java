package com.bho.catchtrippingbackend.attractions.dao;

import com.bho.catchtrippingbackend.attractions.dto.AreaCodes;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AreaCodesDao {

    // Primary Key로 조회
    AreaCodes findByAreaCode(int areaCode);

    // 모든 지역 코드 조회
    List<AreaCodes> findAll();

    // 특정 지역 이름으로 조회 (정확히 일치하는 경우)
    List<AreaCodes> findByAreaName(String areaName);

    // 특정 이름을 포함하는 지역 코드 조회 (부분 일치 검색)
    List<AreaCodes> findByAreaNameContaining(String partialName);
}
