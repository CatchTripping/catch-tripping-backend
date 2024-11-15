package com.bho.catchtrippingbackend.attractions.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContentDetails {
    private int contentId;  // 기본 키 및 외래 키
    private String overview;
    private String homepage;
}
