package com.bho.catchtrippingbackend.attractions.dao;

import com.bho.catchtrippingbackend.attractions.dto.AreaBasedContents;
import com.bho.catchtrippingbackend.attractions.dto.AttractionSearchCriteria;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
@Repository
public interface AreaBasedContentsDao {

    // ID로 데이터 조회
    AreaBasedContents findById(int contentId);

    // 모든 데이터 조회
    List<AreaBasedContents> findAll();

    // 페이징된 데이터 조회
    List<AreaBasedContents> findAllWithPagination(@Param("limit") int limit,
                                                  @Param("offset") int offset);

    // 총 데이터 개수 조회
    int countAll();

    // 특정 지역 코드로 조회
    List<AreaBasedContents> findByAreaCode(int areaCode);

    // 특정 시군구 코드로 조회
    List<AreaBasedContents> findBySigunguCode(int sigunguCode);

    // 카테고리로 조회 (cat1, cat2, cat3 조합)
    List<AreaBasedContents> findByCategories(@Param("cat1") String cat1,
                                             @Param("cat2") String cat2,
                                             @Param("cat3") String cat3);

    // 타이틀을 포함하는 데이터를 조회 (검색어 기반)
    List<AreaBasedContents> findByTitleContaining(String title);

    // 좌표 범위로 조회 (지도의 특정 구역 내 검색)
    List<AreaBasedContents> findByMapCoordinates(@Param("minX") double minX,
                                                 @Param("maxX") double maxX,
                                                 @Param("minY") double minY,
                                                 @Param("maxY") double maxY);

    // 생성일 범위로 조회
    List<AreaBasedContents> findByCreatedTimeBetween(@Param("start") LocalDateTime start,
                                                     @Param("end") LocalDateTime end);

    // 수정일 범위로 조회
    List<AreaBasedContents> findByModifiedTimeBetween(@Param("start") LocalDateTime start,
                                                      @Param("end") LocalDateTime end);

    // 복합 검색 메서드 (AttractionSearchCriteria 활용)
    List<AreaBasedContents> searchAttractions(AttractionSearchCriteria criteria);

    // 복합 검색 결과의 총 개수 조회
    int countSearchAttractions(AttractionSearchCriteria criteria);

    // 복합 검색의 페이징된 데이터 조회
    List<AreaBasedContents> searchAttractionsWithPagination(
            @Param("criteria") AttractionSearchCriteria criteria,
            @Param("limit") int limit,
            @Param("offset") int offset);

    // **추가된 메서드: 지역 코드와 시군구 코드로 조회**
    List<AreaBasedContents> findByAreaCodeAndSigunguCode(@Param("areaCode") int areaCode,
                                                         @Param("sigunguCode") int sigunguCode);
}
