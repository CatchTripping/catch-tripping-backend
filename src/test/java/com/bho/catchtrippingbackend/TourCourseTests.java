package com.bho.catchtrippingbackend;

import com.bho.catchtrippingbackend.tourcourse.controller.TourCourseController;
import com.bho.catchtrippingbackend.tourcourse.dao.CourseDetailDao;
import com.bho.catchtrippingbackend.tourcourse.dto.CourseDetail;
import com.bho.catchtrippingbackend.tourcourse.service.TourCourseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class TourCourseTests {

    @Autowired
    TourCourseService service;

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
}
