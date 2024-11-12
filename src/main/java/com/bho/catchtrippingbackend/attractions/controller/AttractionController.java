package com.bho.catchtrippingbackend.attractions.controller;

import com.bho.catchtrippingbackend.attractions.dto.request.HotPlaceRequest;
import com.bho.catchtrippingbackend.attractions.dto.response.HotPlaceResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/attractions")
public class AttractionController {


    // GET 요청에 대한 응답
    @GetMapping("/hotplace")
    public ResponseEntity<List<HotPlaceResponse>> getHotPlaces(@ModelAttribute HotPlaceRequest hotPlaceRequest) {


        try {
            Thread.sleep(1500);
        } catch (Exception e) {
            // TODO
        }

        List<HotPlaceResponse> contentList = Arrays.asList(
                new HotPlaceResponse("압구정 로데오거리", "http://tong.visitkorea.or.kr/cms/resource/84/3393784_image2_1.JPG", 126823),
                new HotPlaceResponse("청담근린공원", "http://tong.visitkorea.or.kr/cms/resource/00/204200_image2_1.jpg", 127269),
                new HotPlaceResponse("강남", "http://tong.visitkorea.or.kr/cms/resource/08/1984608_image2_1.jpg", 264570),
                new HotPlaceResponse("청담패션거리", "http://tong.visitkorea.or.kr/cms/resource/82/2728682_image2_1.jpg", 1310950),
                new HotPlaceResponse("불국사", "http://tong.visitkorea.or.kr/cms/resource/58/1567758_image2_1.jpg", 1603100),
                new HotPlaceResponse("능인선원", "http://tong.visitkorea.or.kr/cms/resource/72/1567772_image2_1.jpg", 1603149),
                new HotPlaceResponse("브이알존 엑스 코엑스 직영점", "http://tong.visitkorea.or.kr/cms/resource/26/2559926_image2_1.jpg", 2559938),
                new HotPlaceResponse("역삼동성당", "http://tong.visitkorea.or.kr/cms/resource/67/2779567_image2_1.jpg", 2733864)
        );

        // ResponseEntity로 상태 코드와 함께 응답 반환
        return new ResponseEntity<>(contentList, HttpStatus.OK);
    }


}
