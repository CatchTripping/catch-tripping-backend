package com.bho.catchtrippingbackend.attractions.service;

import com.bho.catchtrippingbackend.attractions.dto.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public interface AttractionService {

    // AreaBasedContents 관련 메서드
    AreaBasedContents getAttractionById(int contentId); // ID로 관광지 조회

    List<AreaBasedContents> getAllAttractions(); // 모든 관광지 조회

    List<AreaBasedContents> getAttractionsWithPagination(int page, int pageSize); // 페이징 처리된 관광지 조회

    int getTotalAttractionCount(); // 전체 관광지 수 조회

    List<AreaBasedContents> getAttractionsByAreaCode(int areaCode); // 지역 코드로 관광지 조회

    List<AreaBasedContents> getAttractionsBySigunguCode(int sigunguCode); // 시군구 코드로 관광지 조회

    List<AreaBasedContents> getAttractionsByAreaAndSigungu(int areaCode, int sigunguCode); // 지역 코드와 시군구 코드로 관광지 조회

    List<AreaBasedContents> getAttractionsByCategories(String cat1, String cat2, String cat3); // 카테고리로 관광지 조회

    List<AreaBasedContents> searchAttractionsByTitle(String title); // 제목으로 관광지 검색

    List<AreaBasedContents> getAttractionsByCoordinates(double minX, double maxX, double minY, double maxY); // 지도 좌표로 관광지 조회

    List<AreaBasedContents> getAttractionsByCreatedTime(LocalDateTime start, LocalDateTime end); // 생성일로 관광지 조회

    List<AreaBasedContents> getAttractionsByModifiedTime(LocalDateTime start, LocalDateTime end); // 수정일로 관광지 조회

    // 복합 검색 메서드
    List<AreaBasedContents> searchAttractions(AttractionSearchCriteria criteria); // 복합 조건으로 관광지 검색

    List<AreaBasedContents> searchAttractionsWithPagination(AttractionSearchCriteria criteria, int page, int pageSize); // 페이징된 복합 검색

    int getTotalAttractionCount(AttractionSearchCriteria criteria); // 복합 검색 결과의 총 개수 조회

    // AreaCodes 관련 메서드
    AreaCodes getAreaCode(int areaCode); // 지역 코드 조회

    List<AreaCodes> getAllAreaCodes(); // 모든 지역 코드 조회

    List<AreaCodes> getAreaCodesByName(String areaName); // 지역 이름으로 지역 코드 조회

    List<AreaCodes> searchAreaCodesByName(String partialName); // 부분 일치하는 지역 이름으로 지역 코드 검색

    // SigunguCodes 관련 메서드
    SigunguCodes getSigunguCode(int areaCode, int sigunguCode); // 지역 코드와 시군구 코드로 시군구 조회

    List<SigunguCodes> getSigunguCodesByAreaCode(int areaCode); // 지역 코드로 시군구 목록 조회

    List<SigunguCodes> getAllSigunguCodes(); // 모든 시군구 코드 조회

    List<SigunguCodes> getSigunguCodesByName(String sigunguName); // 시군구 이름으로 조회

    List<SigunguCodes> searchSigunguCodesByName(String partialName); // 부분 일치하는 시군구 이름으로 검색

    // CategoryCodes 관련 메서드
    CategoryCodes getCategoryCode(String categoryCode); // 카테고리 코드 조회

    List<CategoryCodes> getAllCategoryCodes(); // 모든 카테고리 코드 조회

    List<CategoryCodes> getCategoryCodesByName(String categoryName); // 카테고리 이름으로 조회

    List<CategoryCodes> searchCategoryCodesByName(String partialName); // 부분 일치하는 카테고리 이름으로 검색

    // ContentTypes 관련 메서드
    ContentTypes getContentType(int contentTypeId); // 콘텐츠 타입 조회

    List<ContentTypes> getAllContentTypes(); // 모든 콘텐츠 타입 조회

    List<ContentTypes> getContentTypesByName(String contentTypeName); // 콘텐츠 타입 이름으로 조회

    List<ContentTypes> searchContentTypesByName(String partialName); // 부분 일치하는 콘텐츠 타입 이름으로 검색
}
