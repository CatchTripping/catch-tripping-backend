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
            @RequestParam("method") HttpMethod method,
            @RequestParam("type") String type) {

        String key;
        if ("profile".equals(type)) {
            // 프로필 이미지 업로드
            key = "profile-images/temp/" + UUID.randomUUID() + "/" + filename;
        } else if ("board".equals(type)) {
            // 게시물 이미지 업로드 - 임시 폴더에 저장
            key = "board-images/temp/" + UUID.randomUUID() + "/" + filename;
        } else {
            throw new IllegalArgumentException("Invalid type parameter");
        }

        String url = s3Service.generatePresignedUrl(key, method);
        Map<String, String> response = new HashMap<>();
        response.put("url", url);
        response.put("key", key);

        return ResponseEntity.ok(response);
    }
}
