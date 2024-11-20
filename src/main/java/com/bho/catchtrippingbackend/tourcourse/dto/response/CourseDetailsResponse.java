package com.bho.catchtrippingbackend.tourcourse.dto.response;

import com.bho.catchtrippingbackend.tourcourse.dto.CourseDetail;
import lombok.Data;

import java.util.List;

@Data
public class CourseDetailsResponse {
    private String overview;
    private List<CourseDetail> courseDetails;
}