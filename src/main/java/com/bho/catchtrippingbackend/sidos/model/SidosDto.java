package com.bho.catchtrippingbackend.sidos.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SidosDto {
    private int no;
    private int sidoCode;
    private String sidoName;
}
