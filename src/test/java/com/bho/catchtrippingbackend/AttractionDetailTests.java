package com.bho.catchtrippingbackend;

import com.bho.catchtrippingbackend.attractions.dao.AreaBasedContentsDao;
import com.bho.catchtrippingbackend.attractions.dto.response.HotPlaceResponse;
import com.bho.catchtrippingbackend.attractions.service.AttractionService;
import com.bho.catchtrippingbackend.tourcourse.dto.response.CourseDetailsResponse;
import com.bho.catchtrippingbackend.tourcourse.service.TourCourseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AttractionDetailTests {

    @Autowired
    private AttractionService attractionService;

    @Autowired
    private TourCourseService tourCourseService;

    @Test
    void contextLoads() {
        // Spring Boot의 컨텍스트 로딩 확인용 기본 메서드
    }

    @Test
    public void testGetCourseDetails() throws Exception {
        // 테스트할 contentId 설정 (실제 존재하는 코스의 contentId로 변경해야 합니다)
        int contentId = 2988349; // 실제로 존재하는 contentId로 변경하세요

        // 서비스 메서드 호출
        CourseDetailsResponse response = tourCourseService.getCourseDetails(contentId);

        // 응답이 null이 아닌지 확인
        assertNotNull(response, "Response should not be null");

        // overview가 null이 아닌지 확인
        assertNotNull(response.getOverview(), "Overview should not be null");

        // courseDetails 리스트가 null이 아니고 비어있지 않은지 확인
        assertNotNull(response.getCourseDetails(), "CourseDetails list should not be null");
        assertFalse(response.getCourseDetails().isEmpty(), "CourseDetails list should not be empty");

        // 각 CourseDetail에 mapx, mapy가 설정되어 있는지 확인
        response.getCourseDetails().forEach(courseDetail -> {
            assertNotNull(courseDetail.getMapx(), "mapx should not be null for courseDetail");
            assertNotNull(courseDetail.getMapy(), "mapy should not be null for courseDetail");
        });
    }
}
