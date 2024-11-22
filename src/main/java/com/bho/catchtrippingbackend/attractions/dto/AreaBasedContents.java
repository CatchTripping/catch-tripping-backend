package com.bho.catchtrippingbackend.attractions.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AreaBasedContents {
    private String addr1;
    private String addr2;
    private int areacode;
    private int booktour;
    private String cat1;
    private String cat2;
    private String cat3;
    private int contentid; // Primary Key
    private int contenttypeid;
    private LocalDateTime createdtime;
    private String firstimage;
    private String firstimage2;
    private String cpyrhtDivCd;
    private double mapx;
    private double mapy;
    private int mlevel;
    private LocalDateTime modifiedtime;
    private int sigungucode;
    private String tel;
    private String title;
    private String zipcode;
    private String overview; // 추가된 필드
}
