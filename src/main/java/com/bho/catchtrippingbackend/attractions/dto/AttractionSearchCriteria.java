package com.bho.catchtrippingbackend.attractions.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AttractionSearchCriteria {
    private Integer areaCode;
    private Integer sigunguCode;
    private String cat1;
    private String cat2;
    private String cat3;
    private String titleKeyword;
    private Double minX;
    private Double maxX;
    private Double minY;
    private Double maxY;
    private LocalDateTime createdTimeStart;
    private LocalDateTime createdTimeEnd;
    private LocalDateTime modifiedTimeStart;
    private LocalDateTime modifiedTimeEnd;
}
