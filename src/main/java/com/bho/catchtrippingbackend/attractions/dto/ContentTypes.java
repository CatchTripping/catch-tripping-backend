package com.bho.catchtrippingbackend.attractions.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContentTypes {
    private int contentTypeId; // Primary Key
    private String contentTypeName;
}
