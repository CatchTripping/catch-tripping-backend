package com.bho.catchtrippingbackend.attractions.dao;

import com.bho.catchtrippingbackend.attractions.dto.SigunguCodes;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SigunguCodesDao {

    // Primary Key (areaCode와 sigunguCode)로 조회
    SigunguCodes findByAreaCodeAndSigunguCode(int areaCode, int sigunguCode);

    // 특정 지역 코드로 시군구 조회
    List<SigunguCodes> findByAreaCode(int areaCode);

    // 모든 시군구 코드 조회
    List<SigunguCodes> findAll();

    // 특정 시군구 이름으로 조회 (정확히 일치하는 경우)
    List<SigunguCodes> findBySigunguName(String sigunguName);

    // 특정 이름을 포함하는 시군구 조회 (부분 일치 검색)
    List<SigunguCodes> findBySigunguNameContaining(String partialName);
}
