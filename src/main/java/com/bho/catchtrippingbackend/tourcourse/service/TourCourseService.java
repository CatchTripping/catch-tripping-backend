package com.bho.catchtrippingbackend.tourcourse.service;

import com.bho.catchtrippingbackend.tourcourse.dto.CourseDetail;
import com.bho.catchtrippingbackend.tourcourse.dto.request.TourCourseListRequest;
import com.bho.catchtrippingbackend.tourcourse.dto.response.TourCourseSummaryResponse;

import java.util.List;

public interface TourCourseService {
    // 관광 코스의 상세 정보 및 코스 상세 정보 가져오기
    List<CourseDetail> getCourseDetails(int contentId) throws Exception;

    // 새로운 메서드
    TourCourseSummaryResponse getTourCourses(TourCourseListRequest request) throws Exception;
}
