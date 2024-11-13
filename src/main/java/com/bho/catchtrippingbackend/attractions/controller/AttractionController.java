package com.bho.catchtrippingbackend.attractions.controller;

import com.bho.catchtrippingbackend.attractions.dto.request.HotPlaceRequest;
import com.bho.catchtrippingbackend.attractions.dto.response.HotPlaceResponse;
import com.bho.catchtrippingbackend.attractions.service.AttractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
