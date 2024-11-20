package com.bho.catchtrippingbackend.s3.controller;

import com.amazonaws.HttpMethod;
import com.bho.catchtrippingbackend.s3.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/s3")
@RequiredArgsConstructor
public class S3Controller {

    private final S3Service s3Service;

    @GetMapping("/presigned-url")
    public ResponseEntity<Map<String, String>> getPresignedUrl(
            @RequestParam("filename") String filename,
            @RequestParam("method") HttpMethod method) {
        String key = "uploads/" + UUID.randomUUID() + "/" + filename;
        String url = s3Service.generatePresignedUrl(key, method);

        Map<String, String> response = new HashMap<>();
        response.put("url", url);
        response.put("key", key);

        return ResponseEntity.ok(response);
    }
}
