package com.bho.catchtrippingbackend.attractions.dto.response;

import com.bho.catchtrippingbackend.attractions.dto.CategoryCodes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class NearbyAttractionsResponse {

    private List<AttractionSummary> attractions; // 관광지 리스트
    private int totalItems;    // 전체 아이템 수
    private int totalPages;    // 전체 페이지 수
    private int currentPage;   // 현재 페이지 번호

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AttractionSummary {
        private String title;       // 콘텐츠 제목
        private String firstImage;  // 콘텐츠의 첫 번째 이미지 URL
        private int contentId;      // 콘텐츠 고유 ID
        private List<CategoryCodes> categoryCodesList; // 콘텐츠 카테고리 코드 List

        // 추가 필드
        private double mapx;            // 경도
        private double mapy;            // 위도
        private int mlevel;
        private String tel;
        private String zipcode;
        private String addr1;
        private String addr2;
        private int contenttypeid;
        private String contentTypeName; // contenttypeid에 해당하는 명칭
    }
}
