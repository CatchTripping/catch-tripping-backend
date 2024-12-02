package com.bho.catchtrippingbackend.tourcourse.dao;

import com.bho.catchtrippingbackend.tourcourse.dto.CourseDetailImage;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CourseDetailImageDao {

    // 특정 콘텐츠 ID의 이미지 목록 조회
    List<CourseDetailImage> findByContentId(int contentId);

    // 이미지 정보 삽입
    void insertCourseDetailImages(List<CourseDetailImage> images);

    // 특정 콘텐츠 ID의 이미지 정보 삭제
    void deleteByContentId(int contentId);
}
