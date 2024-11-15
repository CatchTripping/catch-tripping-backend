package com.bho.catchtrippingbackend.attractions.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SigunguCodes {
    private int areaCode;      // Primary Key
    private int sigunguCode;    // Primary Key
    private String sigunguName;
}
