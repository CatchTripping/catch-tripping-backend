package com.bho.catchtrippingbackend.tourcourse.dao;

import com.bho.catchtrippingbackend.attractions.dto.AreaBasedContents;
import com.bho.catchtrippingbackend.tourcourse.dto.CourseDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CourseDetailDao {

    // 특정 콘텐츠 ID의 코스 상세 정보 목록 조회
    List<CourseDetail> findByContentId(int contentId);

    // 코스 상세 정보 삽입
    void insertCourseDetails(List<CourseDetail> courseDetails);

    // 코스 상세 정보 삭제 (특정 contentId에 대한 모든 코스 삭제)
    void deleteByContentId(int contentId);
}
