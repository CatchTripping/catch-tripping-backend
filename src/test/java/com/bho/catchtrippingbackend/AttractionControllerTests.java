package com.bho.catchtrippingbackend;

import com.bho.catchtrippingbackend.attractions.dao.AreaBasedContentsDao;
import com.bho.catchtrippingbackend.attractions.dto.*;
import com.bho.catchtrippingbackend.attractions.dto.response.HotPlaceResponse;
import com.bho.catchtrippingbackend.attractions.service.AttractionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AttractionControllerTests {

    @Autowired
    private AreaBasedContentsDao areaBasedContentsDao;
    @Autowired
    private AttractionService attractionService;

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
}
