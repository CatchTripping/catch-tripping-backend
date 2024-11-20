package com.bho.catchtrippingbackend.s3.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3 amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String generatePresignedUrl(String key, HttpMethod method) {
        Date expiration = getExpiration();

        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucket, key)
                .withMethod(method)
                .withExpiration(expiration);

        return amazonS3Client.generatePresignedUrl(request).toString();
    }

    public void deleteObject(String key) {
        amazonS3Client.deleteObject(bucket, key);
    }

    private static Date getExpiration() {
        Date expiration = new Date();
        expiration.setTime(expiration.getTime() + 1000 * 60 * 60); // 1시간

        return expiration;
    }
}
