package com.bho.catchtrippingbackend.attractions.controller;

import com.bho.catchtrippingbackend.attractions.dto.ContentDetails;
import com.bho.catchtrippingbackend.attractions.dto.request.HotPlaceRequest;
import com.bho.catchtrippingbackend.attractions.dto.request.NearbyAttractionsRequest;
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
    public ResponseEntity<List<HotPlaceResponse>> getHotPlaces(@ModelAttribute HotPlaceRequest hotPlaceRequest) {
        List<HotPlaceResponse> hotPlaces = attractionService.getHotPlaces(hotPlaceRequest);
        return new ResponseEntity<>(hotPlaces, HttpStatus.OK);
    }

    @GetMapping("/{id}/details")
    public ResponseEntity<ContentDetails> getContentDetails(@PathVariable("id") int contentId) {
        try {
            ContentDetails contentDetails = attractionService.getContentDetails(contentId);
            return new ResponseEntity<>(contentDetails, HttpStatus.OK);
        } catch (Exception e) {
            // 예외 처리
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/nearby")
    public ResponseEntity<NearbyAttractionsResponse> getNearbyAttractions(
            @RequestParam double latitude,
            @RequestParam double longitude,
            @RequestParam double distance,
            @RequestParam int page,
            @RequestParam int pageSize
    ) {
        NearbyAttractionsRequest request = new NearbyAttractionsRequest();
        request.setLatitude(latitude);
        request.setLongitude(longitude);
        request.setDistance(distance);
        request.setPage(page);
        request.setPageSize(pageSize);

        NearbyAttractionsResponse response = attractionService.getNearbyAttractions(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
