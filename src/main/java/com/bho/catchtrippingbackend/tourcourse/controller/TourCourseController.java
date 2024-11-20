package com.bho.catchtrippingbackend.tourcourse.controller;

import com.bho.catchtrippingbackend.attractions.dto.response.AttractionCustomResponse;
import com.bho.catchtrippingbackend.tourcourse.dto.CourseDetail;
import com.bho.catchtrippingbackend.tourcourse.dto.request.TourCourseListRequest;
import com.bho.catchtrippingbackend.tourcourse.dto.response.CourseDetailsResponse;
import com.bho.catchtrippingbackend.tourcourse.dto.response.TourCourseSummaryResponse;
import com.bho.catchtrippingbackend.tourcourse.service.TourCourseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tourcourses")
public class TourCourseController {

    private final TourCourseService tourCourseService;

    public TourCourseController(TourCourseService tourCourseService) {
        this.tourCourseService = tourCourseService;
    }

    @GetMapping("/{contentId}/details")
    public ResponseEntity<AttractionCustomResponse<CourseDetailsResponse>> getCourseDetails(@PathVariable int contentId) {
        try {
            CourseDetailsResponse response = tourCourseService.getCourseDetails(contentId);
            return ResponseEntity.ok(AttractionCustomResponse.success(response));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(AttractionCustomResponse.error(500, "INTERNAL_SERVER_ERROR", e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<AttractionCustomResponse<TourCourseSummaryResponse>> getTourCourses(
            @ModelAttribute TourCourseListRequest request) {
        try {
            TourCourseSummaryResponse response = tourCourseService.getTourCourses(request);
            return ResponseEntity.ok(AttractionCustomResponse.success(response));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(AttractionCustomResponse.error(500, "INTERNAL_SERVER_ERROR", e.getMessage()));
        }
    }
}
