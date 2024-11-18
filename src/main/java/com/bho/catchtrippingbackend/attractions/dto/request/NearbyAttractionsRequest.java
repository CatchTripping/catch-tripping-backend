package com.bho.catchtrippingbackend.attractions.dto.request;

import lombok.Data;

@Data
public class NearbyAttractionsRequest {

    private double latitude;     // 위도
    private double longitude;    // 경도
    private double distance;     // 거리 (단위: km)
    private int page;            // 페이지 번호
    private int pageSize;        // 한 페이지에 표시할 아이템 수
}
