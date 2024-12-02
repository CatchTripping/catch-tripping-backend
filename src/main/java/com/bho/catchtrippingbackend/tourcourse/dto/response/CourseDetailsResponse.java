package com.bho.catchtrippingbackend.tourcourse.dto.response;

import com.bho.catchtrippingbackend.tourcourse.dto.CourseDetail;
import com.bho.catchtrippingbackend.tourcourse.dto.CourseDetailImage;
import lombok.Data;

import java.util.List;

@Data
public class CourseDetailsResponse {
    private String overview;
    private List<CourseDetail> courseDetails;
//    private List<CourseDetailImage> images; // 추가된 필드
    // 코스의 이미지 정보가 필요하다면 추가
    private List<CourseDetailImage> courseImages;
}