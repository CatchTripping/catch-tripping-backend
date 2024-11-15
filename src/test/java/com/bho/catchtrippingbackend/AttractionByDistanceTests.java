package com.bho.catchtrippingbackend;

import com.bho.catchtrippingbackend.attractions.dao.AreaBasedContentsDao;
import com.bho.catchtrippingbackend.attractions.dao.CategoryCodesDao;
import com.bho.catchtrippingbackend.attractions.dto.CategoryCodes;
import com.bho.catchtrippingbackend.attractions.dto.request.NearbyAttractionsRequest;
import com.bho.catchtrippingbackend.attractions.dto.response.NearbyAttractionsResponse;
import com.bho.catchtrippingbackend.attractions.service.AttractionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class AttractionByDistanceTests {

    @Autowired
    private AttractionService attractionService;
    @Autowired
    private AreaBasedContentsDao dao;
//    @Autowired
//    private CategoryCodesDao dao2;

    @Test
    void contextLoads() {
        // Spring Boot의 컨텍스트 로딩 확인용 기본 메서드
    }

    @Test
    public void testNearbyAttractions() {
//        List<CategoryCodes> list=dao2.getCategoriesByContentId(125266);
//        for(CategoryCodes c:list){
//            System.out.println(c);
//        }
//        System.out.println(dao.countNearbyAttractions(37.5665,126.9780,5));
//        List<NearbyAttractionsResponse.AttractionSummary> list=dao.findNearbyAttractions(37.5665,126.9780,5,10,10);
//        for(NearbyAttractionsResponse.AttractionSummary summary:list){
//            System.out.println(summary);
//    }
        try {
            // 테스트를 위한 요청 생성
            NearbyAttractionsRequest request = new NearbyAttractionsRequest();
            request.setLatitude(37.5665);    // 예시 위도 (서울 시청)
            request.setLongitude(126.9780);  // 예시 경도 (서울 시청)
            request.setDistance(5);          // 5km 반경 내
            request.setPage(1);              // 페이지 번호
            request.setPageSize(10);         // 한 페이지에 표시할 아이템 수

            // 서비스 메서드 호출
            NearbyAttractionsResponse response = attractionService.getNearbyAttractions(request);

            // 결과 출력
            System.out.println("총 아이템 수: " + response.getTotalItems());
            System.out.println("총 페이지 수: " + response.getTotalPages());
            System.out.println("현재 페이지: " + response.getCurrentPage());

            for (NearbyAttractionsResponse.AttractionSummary attraction : response.getAttractions()) {
                System.out.println("제목: " + attraction.getTitle());
                System.out.println("첫 번째 이미지: " + attraction.getFirstImage());
                System.out.println("콘텐츠 ID: " + attraction.getContentId());
                System.out.println("카테고리 코드 리스트: " + attraction.getCategoryCodesList());
                System.out.println("------------------------------");
            }

        } catch (Exception e) {
            System.out.println("에러 발생: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
