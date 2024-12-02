package com.bho.catchtrippingbackend.attractions.controller;

import com.bho.catchtrippingbackend.attractions.dto.ContentDetails;
import com.bho.catchtrippingbackend.attractions.dto.request.HotPlaceRequest;
import com.bho.catchtrippingbackend.attractions.dto.request.NearbyAttractionsRequest;
import com.bho.catchtrippingbackend.attractions.dto.response.AttractionCustomResponse;
import com.bho.catchtrippingbackend.attractions.dto.response.HotPlaceResponse;
import com.bho.catchtrippingbackend.attractions.dto.response.NearbyAttractionsResponse;
import com.bho.catchtrippingbackend.attractions.service.AttractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attractions")
public class AttractionController {

    private final AttractionService attractionService;

    @Autowired
    public AttractionController(AttractionService attractionService) {
        this.attractionService = attractionService;
    }

    // GET 요청에 대한 응답
    @GetMapping("/hotplace")
    public ResponseEntity<AttractionCustomResponse<List<HotPlaceResponse>>> getHotPlaces(@ModelAttribute HotPlaceRequest hotPlaceRequest) {
        try {
            List<HotPlaceResponse> hotPlaces = attractionService.getHotPlaces(hotPlaceRequest);
            return ResponseEntity.ok(AttractionCustomResponse.success(hotPlaces));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(AttractionCustomResponse.error(500, "INTERNAL_SERVER_ERROR", "서버 에러가 발생했습니다."));
        }
    }

    @GetMapping("/{id}/details")
    public ResponseEntity<AttractionCustomResponse<ContentDetails>> getContentDetails(@PathVariable("id") int contentId) {
        try {
            ContentDetails contentDetails = attractionService.getContentDetails(contentId);
            return ResponseEntity.ok(AttractionCustomResponse.success(contentDetails));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(AttractionCustomResponse.error(500, "INTERNAL_SERVER_ERROR", "콘텐츠 상세 정보를 가져오는 중 에러가 발생했습니다."));
        }
    }

    @GetMapping("/nearby")
    public ResponseEntity<AttractionCustomResponse<NearbyAttractionsResponse>> getNearbyAttractions(
            @RequestParam double latitude,
            @RequestParam double longitude,
            @RequestParam double distance,
            @RequestParam int page,
            @RequestParam int pageSize
    ) {
        try {
            NearbyAttractionsRequest request = new NearbyAttractionsRequest();
            request.setLatitude(latitude);
            request.setLongitude(longitude);
            request.setDistance(distance);
            request.setPage(page);
            request.setPageSize(pageSize);

            NearbyAttractionsResponse response = attractionService.getNearbyAttractions(request);
            return ResponseEntity.ok(AttractionCustomResponse.success(response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(AttractionCustomResponse.error(500, "INTERNAL_SERVER_ERROR", "근처 관광지를 가져오는 중 에러가 발생했습니다."));
        }
    }
}
