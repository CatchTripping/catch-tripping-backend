//package com.bho.catchtrippingbackend.travelrecommend.controller;
//
//import com.bho.catchtrippingbackend.attractions.dto.response.AttractionCustomResponse;
//import com.bho.catchtrippingbackend.travelrecommend.dto.reponse.TravelRecommendationResponse;
//import com.bho.catchtrippingbackend.travelrecommend.dto.request.TravelRecommendationRequest;
//import com.bho.catchtrippingbackend.travelrecommend.service.ChatGptService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api/recommendations")
//public class TravelRecommendationController {
//    private final ChatGptService chatGptService;
//
//    @Autowired
//    public TravelRecommendationController(ChatGptService chatGptService) {
//        this.chatGptService = chatGptService;
//    }
//
//    @PostMapping("/travel-destinations")
//    public ResponseEntity<AttractionCustomResponse<TravelRecommendationResponse>> getTravelRecommendations(
//            @RequestBody TravelRecommendationRequest request) {
//        try {
//            TravelRecommendationResponse response = chatGptService.getTravelRecommendations(request);
//            return ResponseEntity.ok(AttractionCustomResponse.success(response));
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(AttractionCustomResponse.error(500, "INTERNAL_SERVER_ERROR", e.getMessage()));
//        }
//    }
//}
