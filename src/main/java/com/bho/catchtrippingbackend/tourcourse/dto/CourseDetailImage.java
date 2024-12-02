package com.bho.catchtrippingbackend.tourcourse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseDetailImage {
    private int id;
    private int contentId;
    private String originimgurl;
    private String smallimageurl;
    private String imgname;
    private String cpyrhtDivCd;
    private String serialnum;
}