package com.bho.catchtrippingbackend.tourcourse.dto.request;

import lombok.Data;

@Data
public class TourCourseListRequest {
    private Integer areaCode;  // 선택적 파라미터
    private Integer sigunguCode; // 선택적 파라미터 추가
    private int page;
    private int pageSize;
}
