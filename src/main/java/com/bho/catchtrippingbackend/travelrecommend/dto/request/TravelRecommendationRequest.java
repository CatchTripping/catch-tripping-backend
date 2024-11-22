package com.bho.catchtrippingbackend.travelrecommend.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class TravelRecommendationRequest {
    private String selectedTravelType;
    private String budgetRange;
    private List<String> activities;
    private String travelDuration;
}
