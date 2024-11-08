package com.bho.catchtrippingbackend;

import com.bho.catchtrippingbackend.attractions.dto.*;
import com.bho.catchtrippingbackend.attractions.service.AttractionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CatchTrippingBackendApplicationTests {

    @Autowired
    private AttractionService attractionService;

    @Test
    void contextLoads() {
        // Spring Boot의 컨텍스트 로딩 확인용 기본 메서드
    }

    @Test
    void testGetAttractionById() {
        int testContentId = 125266; // 존재하는 콘텐츠 ID로 설정해주세요
        AreaBasedContents attraction = attractionService.getAttractionById(testContentId);
        assertNotNull(attraction, "Attraction should not be null");
        assertEquals(testContentId, attraction.getContentid(), "Content ID should match");
        System.out.println(attraction);
    }

    @Test
    void testGetAllAttractions() {
        List<AreaBasedContents> attractions = attractionService.getAllAttractions();
        assertNotNull(attractions, "Attractions list should not be null");
        assertFalse(attractions.isEmpty(), "Attractions list should not be empty");
        System.out.println(attractions);
    }

    @Test
    void testGetAttractionsWithPagination() {
        int page = 1;
        int pageSize = 10;
        List<AreaBasedContents> attractions = attractionService.getAttractionsWithPagination(page, pageSize);
        assertNotNull(attractions, "Attractions list should not be null");
        assertTrue(attractions.size() <= pageSize, "Attractions list size should be less than or equal to page size");
        System.out.println(attractions);
    }

    @Test
    void testGetTotalAttractionCount() {
        int totalCount = attractionService.getTotalAttractionCount();
        assertTrue(totalCount >= 0, "Total count should be zero or positive");
        System.out.println("Total attractions: " + totalCount);
    }

    @Test
    void testGetAttractionsByAreaCode() {
        int testAreaCode = 1; // 존재하는 지역 코드로 설정해주세요
        List<AreaBasedContents> attractions = attractionService.getAttractionsByAreaCode(testAreaCode);
        assertNotNull(attractions, "Attractions list should not be null");
        System.out.println(attractions);
    }

    @Test
    void testGetAttractionsBySigunguCode() {
        int testSigunguCode = 1; // 존재하는 시군구 코드로 설정해주세요
        List<AreaBasedContents> attractions = attractionService.getAttractionsBySigunguCode(testSigunguCode);
        assertNotNull(attractions, "Attractions list should not be null");
        System.out.println(attractions);
    }

    @Test
    void testGetAttractionsByAreaAndSigungu() {
        int testAreaCode = 1; // 존재하는 지역 코드로 설정해주세요
        int testSigunguCode = 1; // 존재하는 시군구 코드로 설정해주세요
        List<AreaBasedContents> attractions = attractionService.getAttractionsByAreaAndSigungu(testAreaCode, testSigunguCode);
        assertNotNull(attractions, "Attractions list should not be null");
        System.out.println(attractions);
    }

    @Test
    void testGetAttractionsByCategories() {
        String cat1 = "A01"; // 존재하는 카테고리 코드로 설정해주세요
        String cat2 = null;
        String cat3 = null;
        List<AreaBasedContents> attractions = attractionService.getAttractionsByCategories(cat1, cat2, cat3);
        assertNotNull(attractions, "Attractions list should not be null");
        System.out.println(attractions);
    }

    @Test
    void testSearchAttractionsByTitle() {
        String titleKeyword = "서울"; // 존재하는 제목 키워드로 설정해주세요
        List<AreaBasedContents> attractions = attractionService.searchAttractionsByTitle(titleKeyword);
        assertNotNull(attractions, "Attractions list should not be null");
        System.out.println(attractions);
    }

    @Test
    void testGetAttractionsByCoordinates() {
        double minX = 126.0;
        double maxX = 127.0;
        double minY = 37.0;
        double maxY = 38.0;
        List<AreaBasedContents> attractions = attractionService.getAttractionsByCoordinates(minX, maxX, minY, maxY);
        assertNotNull(attractions, "Attractions list should not be null");
        System.out.println(attractions);
    }

    @Test
    void testGetAttractionsByCreatedTime() {
        LocalDateTime start = LocalDateTime.of(2020, 1, 1, 0, 0);
        LocalDateTime end = LocalDateTime.now();
        List<AreaBasedContents> attractions = attractionService.getAttractionsByCreatedTime(start, end);
        assertNotNull(attractions, "Attractions list should not be null");
        System.out.println(attractions);
    }

    @Test
    void testGetAttractionsByModifiedTime() {
        LocalDateTime start = LocalDateTime.of(2020, 1, 1, 0, 0);
        LocalDateTime end = LocalDateTime.now();
        List<AreaBasedContents> attractions = attractionService.getAttractionsByModifiedTime(start, end);
        assertNotNull(attractions, "Attractions list should not be null");
        System.out.println(attractions);
    }

    @Test
    void testSearchAttractions() {
        AttractionSearchCriteria criteria = AttractionSearchCriteria.builder()
                .areaCode(1) // 존재하는 지역 코드로 설정해주세요
                .cat1("A01") // 존재하는 카테고리 코드로 설정해주세요
                .titleKeyword("서울") // 존재하는 제목 키워드로 설정해주세요
                .build();

        List<AreaBasedContents> attractions = attractionService.searchAttractions(criteria);
        assertNotNull(attractions, "Attractions list should not be null");
        System.out.println(attractions);
    }

    @Test
    void testSearchAttractionsWithPagination() {
        AttractionSearchCriteria criteria = AttractionSearchCriteria.builder()
                .areaCode(1)
                .cat1("A01")
                .titleKeyword("서울")
                .build();
        int page = 1;
        int pageSize = 10;
        List<AreaBasedContents> attractions = attractionService.searchAttractionsWithPagination(criteria, page, pageSize);
        assertNotNull(attractions, "Attractions list should not be null");
        assertTrue(attractions.size() <= pageSize, "Attractions list size should be less than or equal to page size");
        System.out.println(attractions);
    }

    @Test
    void testGetTotalAttractionCountWithCriteria() {
        AttractionSearchCriteria criteria = AttractionSearchCriteria.builder()
                .areaCode(1)
                .cat1("A01")
                .titleKeyword("서울")
                .build();
        int totalCount = attractionService.getTotalAttractionCount(criteria);
        assertTrue(totalCount >= 0, "Total count should be zero or positive");
        System.out.println("Total attractions with criteria: " + totalCount);
    }

    // AreaCodes 관련 테스트 메서드

    @Test
    void testGetAreaCode() {
        int testAreaCode = 1; // 존재하는 지역 코드로 설정해주세요
        AreaCodes areaCode = attractionService.getAreaCode(testAreaCode);
        assertNotNull(areaCode, "AreaCode should not be null");
        assertEquals(testAreaCode, areaCode.getAreaCode(), "AreaCode should match");
        System.out.println(areaCode);
    }

    @Test
    void testGetAllAreaCodes() {
        List<AreaCodes> areaCodes = attractionService.getAllAreaCodes();
        assertNotNull(areaCodes, "AreaCodes list should not be null");
        assertFalse(areaCodes.isEmpty(), "AreaCodes list should not be empty");
        System.out.println(areaCodes);
    }

    @Test
    void testGetAreaCodesByName() {
        String areaName = "서울"; // 존재하는 지역 이름으로 설정해주세요
        List<AreaCodes> areaCodes = attractionService.getAreaCodesByName(areaName);
        assertNotNull(areaCodes, "AreaCodes list should not be null");
        System.out.println(areaCodes);
    }

    @Test
    void testSearchAreaCodesByName() {
        String partialName = "서"; // 부분 일치하는 지역 이름
        List<AreaCodes> areaCodes = attractionService.searchAreaCodesByName(partialName);
        assertNotNull(areaCodes, "AreaCodes list should not be null");
        System.out.println(areaCodes);
    }

    // SigunguCodes 관련 테스트 메서드

    @Test
    void testGetSigunguCode() {
        int testAreaCode = 1; // 존재하는 지역 코드로 설정해주세요
        int testSigunguCode = 1; // 존재하는 시군구 코드로 설정해주세요
        SigunguCodes sigunguCode = attractionService.getSigunguCode(testAreaCode, testSigunguCode);
        assertNotNull(sigunguCode, "SigunguCode should not be null");
        assertEquals(testAreaCode, sigunguCode.getAreaCode(), "AreaCode should match");
        assertEquals(testSigunguCode, sigunguCode.getSigunguCode(), "SigunguCode should match");
        System.out.println(sigunguCode);
    }

    @Test
    void testGetSigunguCodesByAreaCode() {
        int testAreaCode = 1; // 존재하는 지역 코드로 설정해주세요
        List<SigunguCodes> sigunguCodes = attractionService.getSigunguCodesByAreaCode(testAreaCode);
        assertNotNull(sigunguCodes, "SigunguCodes list should not be null");
        System.out.println(sigunguCodes);
    }

    @Test
    void testGetAllSigunguCodes() {
        List<SigunguCodes> sigunguCodes = attractionService.getAllSigunguCodes();
        assertNotNull(sigunguCodes, "SigunguCodes list should not be null");
        assertFalse(sigunguCodes.isEmpty(), "SigunguCodes list should not be empty");
        System.out.println(sigunguCodes);
    }

    @Test
    void testGetSigunguCodesByName() {
        String sigunguName = "종로구"; // 존재하는 시군구 이름으로 설정해주세요
        List<SigunguCodes> sigunguCodes = attractionService.getSigunguCodesByName(sigunguName);
        assertNotNull(sigunguCodes, "SigunguCodes list should not be null");
        System.out.println(sigunguCodes);
    }

    @Test
    void testSearchSigunguCodesByName() {
        String partialName = "종"; // 부분 일치하는 시군구 이름
        List<SigunguCodes> sigunguCodes = attractionService.searchSigunguCodesByName(partialName);
        assertNotNull(sigunguCodes, "SigunguCodes list should not be null");
        System.out.println(sigunguCodes);
    }

    // CategoryCodes 관련 테스트 메서드

    @Test
    void testGetCategoryCode() {
        String testCategoryCode = "A01"; // 존재하는 카테고리 코드로 설정해주세요
        CategoryCodes categoryCode = attractionService.getCategoryCode(testCategoryCode);
        assertNotNull(categoryCode, "CategoryCode should not be null");
        assertEquals(testCategoryCode, categoryCode.getCategoryCode(), "CategoryCode should match");
        System.out.println(categoryCode);
    }

    @Test
    void testGetAllCategoryCodes() {
        List<CategoryCodes> categoryCodes = attractionService.getAllCategoryCodes();
        assertNotNull(categoryCodes, "CategoryCodes list should not be null");
        assertFalse(categoryCodes.isEmpty(), "CategoryCodes list should not be empty");
        System.out.println(categoryCodes);
    }

    @Test
    void testGetCategoryCodesByName() {
        String categoryName = "관광지"; // 존재하는 카테고리 이름으로 설정해주세요
        List<CategoryCodes> categoryCodes = attractionService.getCategoryCodesByName(categoryName);
        assertNotNull(categoryCodes, "CategoryCodes list should not be null");
        System.out.println(categoryCodes);
    }

    @Test
    void testSearchCategoryCodesByName() {
        String partialName = "관"; // 부분 일치하는 카테고리 이름
        List<CategoryCodes> categoryCodes = attractionService.searchCategoryCodesByName(partialName);
        assertNotNull(categoryCodes, "CategoryCodes list should not be null");
        System.out.println(categoryCodes);
    }

     //ContentTypes 관련 테스트 메서드

    @Test
    void testGetContentType() {
        int testContentTypeId = 12; // 존재하는 콘텐츠 타입 ID로 설정해주세요
        ContentTypes contentType = attractionService.getContentType(testContentTypeId);
        assertNotNull(contentType, "ContentType should not be null");
        assertEquals(testContentTypeId, contentType.getContentTypeId(), "ContentTypeId should match");
        System.out.println(contentType);
    }

    @Test
    void testGetAllContentTypes() {
        List<ContentTypes> contentTypes = attractionService.getAllContentTypes();
        assertNotNull(contentTypes, "ContentTypes list should not be null");
        assertFalse(contentTypes.isEmpty(), "ContentTypes list should not be empty");
        System.out.println(contentTypes);
    }

    @Test
    void testGetContentTypesByName() {
        String contentTypeName = "숙박"; // 존재하는 콘텐츠 타입 이름으로 설정해주세요
        List<ContentTypes> contentTypes = attractionService.getContentTypesByName(contentTypeName);
        assertNotNull(contentTypes, "ContentTypes list should not be null");
        System.out.println(contentTypes);
    }

    @Test
    void testSearchContentTypesByName() {
        String partialName = "숙"; // 부분 일치하는 콘텐츠 타입 이름
        List<ContentTypes> contentTypes = attractionService.searchContentTypesByName(partialName);
        assertNotNull(contentTypes, "ContentTypes list should not be null");
        System.out.println(contentTypes);
    }
}
