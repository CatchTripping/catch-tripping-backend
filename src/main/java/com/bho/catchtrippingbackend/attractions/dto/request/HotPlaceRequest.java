package com.bho.catchtrippingbackend.attractions.dto.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotPlaceRequest {
    private int page;              // 페이지 번호
    private int size;              // 페이지 당 항목 개수
    private String hotPlaceType;   // 장소 유형 (예: Restaurant)
    private int regionCode;        // 지역 코드
    private List<Integer> sigunguCodes;  // 시군구 코드 (리스트로 처리)
}
