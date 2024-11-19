package com.bho.catchtrippingbackend.tourcourse.controller;

import com.bho.catchtrippingbackend.attractions.dto.response.AttractionCustomResponse;
import com.bho.catchtrippingbackend.tourcourse.dto.CourseDetail;
import com.bho.catchtrippingbackend.tourcourse.service.TourCourseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tourcourses")
public class TourCourseController {

    private final TourCourseService tourCourseService;

    public TourCourseController(TourCourseService tourCourseService) {
        this.tourCourseService = tourCourseService;
    }

    @GetMapping("/{contentId}/details")
    public ResponseEntity<AttractionCustomResponse<List<CourseDetail>>> getCourseDetails(@PathVariable int contentId) {
        try {
            List<CourseDetail> courseDetails = tourCourseService.getCourseDetails(contentId);
            return ResponseEntity.ok(AttractionCustomResponse.success(courseDetails));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(AttractionCustomResponse.error(500, "INTERNAL_SERVER_ERROR", e.getMessage()));
        }
    }
}
