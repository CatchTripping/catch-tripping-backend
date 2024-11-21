package com.bho.catchtrippingbackend;

import com.bho.catchtrippingbackend.attractions.dao.AreaBasedContentsDao;
import com.bho.catchtrippingbackend.attractions.dto.response.HotPlaceResponse;
import com.bho.catchtrippingbackend.attractions.service.AttractionService;
import com.bho.catchtrippingbackend.tourcourse.dto.CourseDetail;
import com.bho.catchtrippingbackend.tourcourse.dto.CourseDetailImage;
import com.bho.catchtrippingbackend.tourcourse.dto.response.CourseDetailsResponse;
import com.bho.catchtrippingbackend.tourcourse.service.TourCourseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AttractionImageTests {

    @Autowired
    private TourCourseService tourCourseService;

    @Test
    void contextLoads() {
        // Spring Boot의 컨텍스트 로딩 확인용 기본 메서드
    }


    @Test
    public void testImage() throws Exception {
        int contentId = 1846248; // 실제 존재하는 contentId 사용

        CourseDetailsResponse response = tourCourseService.getCourseDetails(contentId);

        // 검증
        System.out.println("overview");
        System.out.println(response.getOverview());

        System.out.println("details");
        List<CourseDetail> courseDetailList=response.getCourseDetails();
        for (CourseDetail courseDetail : courseDetailList) {
            System.out.println(courseDetail.getSubName());
            System.out.println(courseDetail.getId());
        }
        System.out.println("images");
        List<CourseDetailImage> courseDetailImageList=response.getCourseImages();
        for (CourseDetailImage courseDetailImage : courseDetailImageList) {
            System.out.println(courseDetailImage);
        }

//        assertNotNull(response);
//        assertNotNull(response.getOverview());
//        assertNotNull(response.getCourseDetails());
//        assertFalse(response.getCourseDetails().isEmpty());
//        assertNotNull(response.getImages());
//        assertFalse(response.getImages().isEmpty());

        // 추가적인 검증 로직 작성
    }

}
