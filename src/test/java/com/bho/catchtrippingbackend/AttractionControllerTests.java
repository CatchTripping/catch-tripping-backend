package com.bho.catchtrippingbackend;

import com.bho.catchtrippingbackend.attractions.controller.AttractionController;
import com.bho.catchtrippingbackend.attractions.dao.AreaBasedContentsDao;
import com.bho.catchtrippingbackend.attractions.dto.*;
import com.bho.catchtrippingbackend.attractions.dto.request.HotPlaceRequest;
import com.bho.catchtrippingbackend.attractions.dto.response.AttractionCustomResponse;
import com.bho.catchtrippingbackend.attractions.dto.response.HotPlaceResponse;
import com.bho.catchtrippingbackend.attractions.service.AttractionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AttractionControllerTests {

    @Autowired
    private AreaBasedContentsDao areaBasedContentsDao;
    @Autowired
    private AttractionService attractionService;

    @Autowired
    private AttractionController attractionController;

    @Test
    void contextLoads() {
        // Spring Boot의 컨텍스트 로딩 확인용 기본 메서드
    }

    @Test
    public void testFindHotPlaces() {
        List<Integer> sigunguCodes = List.of(4, 5);
        List<HotPlaceResponse> hotPlaces = areaBasedContentsDao.findHotPlaces(
                "Restaurant", 1, sigunguCodes, 8, 0);
        assertNotNull(hotPlaces);
        assertEquals(8, hotPlaces.size());
    }

    @Test
    public void testGetHotPlacesController() {
        // 테스트를 위한 요청 객체 생성
        HotPlaceRequest hotPlaceRequest = new HotPlaceRequest();
        hotPlaceRequest.setPage(1);
        hotPlaceRequest.setOffset(8);
        hotPlaceRequest.setHotPlaceType("Restaurant");
        hotPlaceRequest.setRegionCode(1);
        hotPlaceRequest.setSigunguCodes(List.of(4, 5));

        // 컨트롤러의 메서드 호출
        ResponseEntity<AttractionCustomResponse<List<HotPlaceResponse>>> responseEntity =
                attractionController.getHotPlaces(hotPlaceRequest);

        // 응답 검증
        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());

        AttractionCustomResponse<List<HotPlaceResponse>> responseBody = responseEntity.getBody();
        assertNotNull(responseBody);
        assertEquals(200, responseBody.getStatus());
        assertEquals("SUCCESS", responseBody.getCode());
        assertNotNull(responseBody.getData());

        // 데이터 검증
        List<HotPlaceResponse> hotPlaces = responseBody.getData();
        assertFalse(hotPlaces.isEmpty());

        // 첫 번째 항목 검증 (예시)
        HotPlaceResponse firstHotPlace = hotPlaces.get(0);
        assertNotNull(firstHotPlace.getTitle());
        assertNotNull(firstHotPlace.getContentId());
        // 필요한 경우 추가 검증 수행
    }
}
