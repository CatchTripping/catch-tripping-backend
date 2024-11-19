package com.bho.catchtrippingbackend.tourcourse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDetail {
    private int id;
    private int contentId;
    private int subContentId;
    private String subName;
    private String subOverview;
    private String subDetailImg;
    private String subDetailAlt;
    private int serialnum;
    private int subnum;
    private String createdTime;
}