package com.bho.catchtrippingbackend.guguns.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GugunsDto {
    private int no;
    private int sidoCode;
    private int gugunCode;
    private String gugunName;
}
