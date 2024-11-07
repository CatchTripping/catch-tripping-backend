package com.bho.catchtrippingbackend.attractions.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AreaCodes {
    private int areaCode; // Primary Key
    private String areaName;
}
