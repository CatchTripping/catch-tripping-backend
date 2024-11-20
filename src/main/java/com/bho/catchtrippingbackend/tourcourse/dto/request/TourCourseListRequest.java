package com.bho.catchtrippingbackend.tourcourse.dto.request;

import lombok.Data;

@Data
public class TourCourseListRequest {
    private Integer areaCode;  // 선택적 파라미터
    private int page;
    private int pageSize;
}
