package com.bho.catchtrippingbackend.s3.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3 amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String generatePresignedUrl(String key, HttpMethod method) {
        if (key == null || key.isEmpty()) {
            return null;
        }
        Date expiration = getExpiration();

        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucket, key)
                .withMethod(method)
                .withExpiration(expiration);

        return amazonS3Client.generatePresignedUrl(request).toString();
    }

    public void deleteObject(String key) {
        amazonS3Client.deleteObject(bucket, key);
    }

    // 특정 prefix(폴더)의 모든 객체 삭제
    public void deleteFolder(String folderPath) {
        ListObjectsV2Request listObjectsRequest = new ListObjectsV2Request()
                .withBucketName(bucket)
                .withPrefix(folderPath);

        List<S3ObjectSummary> objectSummaries = new ArrayList<>();

        ListObjectsV2Result result;
        do {
            result = amazonS3Client.listObjectsV2(listObjectsRequest);
            objectSummaries.addAll(result.getObjectSummaries());
            listObjectsRequest.setContinuationToken(result.getNextContinuationToken());
        } while(result.isTruncated());

        if (!objectSummaries.isEmpty()) {
            DeleteObjectsRequest deleteObjectsRequest = new DeleteObjectsRequest(bucket);
            List<DeleteObjectsRequest.KeyVersion> keys = objectSummaries.stream()
                    .map(s3ObjectSummary -> new DeleteObjectsRequest.KeyVersion(s3ObjectSummary.getKey()))
                    .collect(Collectors.toList());
            deleteObjectsRequest.setKeys(keys);
            amazonS3Client.deleteObjects(deleteObjectsRequest);
        }
    }

    public void moveObject(String sourceKey, String destinationKey) {
        CopyObjectRequest copyObjRequest = new CopyObjectRequest(bucket, sourceKey, bucket, destinationKey)
                .withCannedAccessControlList(CannedAccessControlList.Private);
        amazonS3Client.copyObject(copyObjRequest);
        deleteObject(sourceKey);
    }

    private static Date getExpiration() {
        Date expiration = new Date();
        expiration.setTime(expiration.getTime() + 1000 * 60 * 60); // 1시간

        return expiration;
    }

    public List<String> generatePresignedUrls(List<String> keys, HttpMethod method) {
        return keys.stream()
                .map(key -> generatePresignedUrl(key, method))
                .collect(Collectors.toList());
    }
}
