package com.bho.catchtrippingbackend.attractions.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttractionsDto {
    private int no;
    private Integer contentId; // Using Integer to allow for null values
    private String title;
    private Integer contentTypeId;
    private Integer areaCode;
    private Integer siGunGuCode;
    private String firstImage1;
    private String firstImage2;
    private Integer mapLevel;
    private Double latitude; // Using Double for latitude
    private Double longitude; // Using Double for longitude
    private String tel;
    private String addr1;
    private String addr2;
    private String homepage;
    private String overview;
}
