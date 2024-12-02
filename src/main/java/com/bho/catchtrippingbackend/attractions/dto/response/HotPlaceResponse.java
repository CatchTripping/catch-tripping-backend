package com.bho.catchtrippingbackend.attractions.dto.response;

import com.bho.catchtrippingbackend.attractions.dto.CategoryCodes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotPlaceResponse {
    private String title;       // 콘텐츠 제목
    private String firstImage;  // 콘텐츠의 첫 번째 이미지 URL
    private int contentId;      // 콘텐츠 고유 ID
    private List<CategoryCodes> categoryCodesList; // 콘텐츠 카테고리 코드 List
}
