package com.bho.catchtrippingbackend.tourcourse.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class TourCourseSummaryResponse {

    private List<TourCourseSummary> courses;
    private int totalItems;
    private int totalPages;
    private int currentPage;

    @Data
    public static class TourCourseSummary {
        private int contentId;
        private String title;
        private String cat3;
        private String firstImage;
        private String firstImage2;
        private int areaCode;
        private String areaName;
        private int sigunguCode;
        private String sigunguName;
        private List<String> destinations;  // 포함된 여행지의 이름 목록
    }
}
