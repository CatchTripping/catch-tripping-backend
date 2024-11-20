package com.bho.catchtrippingbackend;

import com.bho.catchtrippingbackend.attractions.dao.AreaBasedContentsDao;
import com.bho.catchtrippingbackend.attractions.dto.request.NearbyAttractionsRequest;
import com.bho.catchtrippingbackend.attractions.dto.response.NearbyAttractionsResponse;
import com.bho.catchtrippingbackend.attractions.service.AttractionService;
import com.bho.catchtrippingbackend.travelrecommend.dto.reponse.TravelRecommendationResponse;
import com.bho.catchtrippingbackend.travelrecommend.dto.request.TravelRecommendationRequest;
import com.bho.catchtrippingbackend.travelrecommend.service.ChatGptService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest

class TravelRecommendationServiceTests {

    private static final Logger logger = LoggerFactory.getLogger(TravelRecommendationServiceTests.class);

    @Autowired
    private ChatGptService chatGptService;

    @Test
    void contextLoads() {
        // Spring Boot의 컨텍스트 로딩 확인용 기본 메서드
    }

    @Test
    void testGptService() {
        TravelRecommendationRequest request = new TravelRecommendationRequest();
        request.setSelectedTravelType("이별 후 휴식");
        request.setBudgetRange("10만원 이하");
        request.setActivities(Arrays.asList("휴식"));
        request.setTravelDuration("2일");

        try {
            // ChatGptService의 메서드 호출
            TravelRecommendationResponse response = chatGptService.getTravelRecommendations(request);

            // 응답 출력
            logger.info("ChatGPT Response: {}", response);

            // 응답에 대한 검증
            assertNotNull(response, "응답이 null입니다.");
            assertNotNull(response.getDestinations(), "Destination 리스트가 null입니다.");
            assertFalse(response.getDestinations().isEmpty(), "Destination 리스트가 비어 있습니다.");

            // 각 Destination에 대한 상세 검증 (예: 이름과 설명이 null이 아닌지)
            for (TravelRecommendationResponse.Destination destination : response.getDestinations()) {
                assertNotNull(destination.getName(), "Destination의 이름이 null입니다.");
                assertNotNull(destination.getDescription(), "Destination의 설명이 null입니다.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            fail("예외 발생: " + e.getMessage());
        }
    }
}
