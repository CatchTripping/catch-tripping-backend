package com.bho.catchtrippingbackend.tourcourse.service;

import com.bho.catchtrippingbackend.tourcourse.dto.CourseDetail;

import java.util.List;

public interface TourCourseService {
    // 관광 코스의 상세 정보 및 코스 상세 정보 가져오기
    List<CourseDetail> getCourseDetails(int contentId) throws Exception;
}
