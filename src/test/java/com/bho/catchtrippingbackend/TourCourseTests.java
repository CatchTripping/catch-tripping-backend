package com.bho.catchtrippingbackend;

import com.bho.catchtrippingbackend.attractions.dao.AreaBasedContentsDao;
import com.bho.catchtrippingbackend.tourcourse.controller.TourCourseController;
import com.bho.catchtrippingbackend.tourcourse.dao.CourseDetailDao;
import com.bho.catchtrippingbackend.tourcourse.dto.CourseDetail;
import com.bho.catchtrippingbackend.tourcourse.dto.request.TourCourseListRequest;
import com.bho.catchtrippingbackend.tourcourse.dto.response.TourCourseSummaryResponse;
import com.bho.catchtrippingbackend.tourcourse.service.TourCourseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

@SpringBootTest
public class TourCourseTests {

    @Autowired
    TourCourseService service;

    @Autowired
    AreaBasedContentsDao dao;

    @Test
    void contextLoads() throws Exception {

    }

    @Test
    void testTourcourse() throws Exception {
        // Spring Boot의 컨텍스트 로딩 확인용 기본 메서드
        List<CourseDetail> list=service.getCourseDetails(1846248);
        for (CourseDetail courseDetail : list) {
            System.out.println("항목 구분");
            System.out.println(courseDetail);
        }
    }

    @Test
    void testTourcourseList() throws Exception {
        System.out.println(dao.countTourCourses(null));
    }

    @Test
    void testGetTourCourses() throws Exception {
        // 서비스 레이어의 getTourCourses 메서드 테스트
        TourCourseListRequest request = new TourCourseListRequest();
        request.setAreaCode(1); // 예시로 서울 지역 코드 사용
        request.setPage(1);
        request.setPageSize(10);

        TourCourseSummaryResponse response = service.getTourCourses(request);

        // 응답 검증
        assertNotNull(response, "응답이 null입니다.");
        assertNotNull(response.getCourses(), "코스 리스트가 null입니다.");
        assertFalse(response.getCourses().isEmpty(), "코스 리스트가 비어 있습니다.");

        // 각 코스 요약 정보 출력 및 검증
        for (TourCourseSummaryResponse.TourCourseSummary courseSummary : response.getCourses()) {
            System.out.println("코스 요약 정보:");
            System.out.println(courseSummary);

            // 필드 검증
            assertNotNull(courseSummary.getTitle(), "코스 제목이 null입니다.");
            assertNotNull(courseSummary.getAreaName(), "지역 이름이 null입니다.");
            assertNotNull(courseSummary.getDestinations(), "여행지 목록이 null입니다.");
        }

        // 페이징 정보 검증
        assertEquals(request.getPage(), response.getCurrentPage(), "현재 페이지 번호가 일치하지 않습니다.");
        assertTrue(response.getTotalItems() >= response.getCourses().size(), "전체 아이템 수가 코스 수보다 작습니다.");
    }
}
