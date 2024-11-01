package com.bho.catchtrippingbackend.contenttypes.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContentTypesDto {
    private int contentTypeId;
    private String contentTypeName;
}
