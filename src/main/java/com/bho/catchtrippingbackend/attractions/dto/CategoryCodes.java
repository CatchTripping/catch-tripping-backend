package com.bho.catchtrippingbackend.attractions.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryCodes {
    private String categoryCode; // Primary Key
    private String categoryName;
}
