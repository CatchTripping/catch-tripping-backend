package com.bho.catchtrippingbackend.tourcourse.service;

import com.bho.catchtrippingbackend.attractions.dao.AreaBasedContentsDao;
import com.bho.catchtrippingbackend.attractions.dao.AreaCodesDao;
import com.bho.catchtrippingbackend.attractions.dao.ContentDetailsDao;
import com.bho.catchtrippingbackend.attractions.dao.SigunguCodesDao;
import com.bho.catchtrippingbackend.attractions.dto.AreaBasedContents;
import com.bho.catchtrippingbackend.attractions.dto.AreaCodes;
import com.bho.catchtrippingbackend.attractions.dto.ContentDetails;
import com.bho.catchtrippingbackend.attractions.dto.SigunguCodes;
import com.bho.catchtrippingbackend.tourcourse.dao.CourseDetailDao;
import com.bho.catchtrippingbackend.tourcourse.dto.CourseDetail;
import com.bho.catchtrippingbackend.tourcourse.dto.request.TourCourseListRequest;
import com.bho.catchtrippingbackend.tourcourse.dto.response.TourCourseSummaryResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Service
public class TourCourseServiceImpl implements TourCourseService {
    private final CourseDetailDao courseDetailDao;
    private final ContentDetailsDao contentDetailsDao;
    private final AreaCodesDao areaCodesDao;
    private final SigunguCodesDao sigunguCodesDao;
    private final AreaBasedContentsDao areaBasedContentsDao;

    @Value("${tourapi.serviceKey}")
    private String serviceKey;

    @Value("${tourapi.tourcourse.detailInfoUrl}")
    private String detailInfoUrl;

    public TourCourseServiceImpl(CourseDetailDao courseDetailDao,
                                 ContentDetailsDao contentDetailsDao,
                                 AreaCodesDao areaCodesDao,
                                 SigunguCodesDao sigunguCodesDao,
                                 AreaBasedContentsDao areaBasedContentsDao) {
        this.courseDetailDao = courseDetailDao;
        this.contentDetailsDao = contentDetailsDao;
        this.areaCodesDao = areaCodesDao;
        this.sigunguCodesDao = sigunguCodesDao;
        this.areaBasedContentsDao = areaBasedContentsDao;
    }

    @Override
    public List<CourseDetail> getCourseDetails(int contentId) throws Exception {
        // 데이터베이스에서 조회
        List<CourseDetail> courseDetails = courseDetailDao.findByContentId(contentId);
        if (courseDetails != null && !courseDetails.isEmpty()) {
            return courseDetails;
        } else {
            // API 호출하여 데이터 가져오기
            courseDetails = fetchCourseDetailsFromAPI(contentId);

            if (courseDetails != null && !courseDetails.isEmpty()) {
                // 데이터베이스에 저장하기 전에 기존 데이터를 삭제
                courseDetailDao.deleteByContentId(contentId);
                // 데이터베이스에 저장
                courseDetailDao.insertCourseDetails(courseDetails);
                return courseDetails;
            } else {
                throw new Exception("API에서 코스 상세 정보를 가져오지 못했습니다.");
            }
        }
    }

    private List<CourseDetail> fetchCourseDetailsFromAPI(int contentId) throws Exception {
        RestTemplate restTemplate = new RestTemplate();

        // detailInfo API 호출하여 코스 상세 정보 가져오기
        URI infoUrl = UriComponentsBuilder.fromHttpUrl(detailInfoUrl)
                .queryParam("serviceKey", serviceKey)
                .queryParam("MobileOS", "ETC")
                .queryParam("MobileApp", "AppTest")
                .queryParam("contentId", contentId)
                .queryParam("contentTypeId", 25) // 관광 코스 타입 ID
                .queryParam("_type", "json")
                .build(true)
                .toUri();

        String infoResponse = restTemplate.getForObject(infoUrl, String.class);

        // JSON 파싱
        ObjectMapper objectMapper = new ObjectMapper();

        // infoResponse 파싱
        JsonNode infoRoot = objectMapper.readTree(infoResponse);
        JsonNode responseNode = infoRoot.path("response");
        JsonNode headerNode = responseNode.path("header");
        String resultCode = headerNode.path("resultCode").asText();

        if (!"0000".equals(resultCode)) {
            String resultMsg = headerNode.path("resultMsg").asText();
            throw new Exception("API 에러: " + resultMsg);
        }

        JsonNode bodyNode = responseNode.path("body");
        JsonNode itemsNode = bodyNode.path("items");
        JsonNode itemArray = itemsNode.path("item");

        if (itemArray.isMissingNode()) {
            throw new Exception("API 응답에 item이 없습니다.");
        }

        List<CourseDetail> courseDetails = new ArrayList<>();

        if (itemArray.isArray()) {
            for (JsonNode itemNode : itemArray) {
                CourseDetail detail = parseCourseDetail(itemNode, contentId);
                courseDetails.add(detail);
            }
        } else {
            // item이 단일 객체인 경우
            CourseDetail detail = parseCourseDetail(itemArray, contentId);
            courseDetails.add(detail);
        }

        // 추가로 content_details에 infoname과 infotext 저장 (필요한 경우)
        saveContentDetails(contentId, itemsNode);

        return courseDetails;
    }

    private CourseDetail parseCourseDetail(JsonNode itemNode, int contentId) {
        int subContentId = itemNode.path("subcontentid").asInt();
        String subName = itemNode.path("subname").asText();
        String subOverview = itemNode.path("subdetailoverview").asText();
        String subDetailImg = itemNode.path("subdetailimg").asText();
        String subDetailAlt = itemNode.path("subdetailalt").asText();
        int serialnum = itemNode.path("serialnum").asInt();
        int subnum = itemNode.path("subnum").asInt();

        CourseDetail detail = new CourseDetail();
        detail.setContentId(contentId);
        detail.setSubContentId(subContentId);
        detail.setSubName(subName);
        detail.setSubOverview(subOverview);
        detail.setSubDetailImg(subDetailImg);
        detail.setSubDetailAlt(subDetailAlt);
        detail.setSerialnum(serialnum);
        detail.setSubnum(subnum);

        return detail;
    }

    private void saveContentDetails(int contentId, JsonNode itemsNode) {
        // content_details 테이블에 infoname과 infotext 저장
        JsonNode itemNode = itemsNode.path("item");
        if (itemNode.isArray()) {
            itemNode = itemNode.get(0); // 첫 번째 아이템 사용
        }

        String infoname = itemNode.path("infoname").asText();
        String infotext = itemNode.path("infotext").asText();
        int fldgubun = itemNode.path("fldgubun").asInt();

        // content_details에 해당 contentId가 있는지 확인
        ContentDetails contentDetails = contentDetailsDao.findByContentId(contentId);
        if (contentDetails == null) {
            // 없다면 새로운 레코드 삽입
            contentDetails = new ContentDetails();
            contentDetails.setContentId(contentId);
            contentDetails.setOverview(null); // overview는 null로 설정하거나 기본값으로 설정
            contentDetails.setHomepage(null); // homepage는 null로 설정하거나 기본값으로 설정

            contentDetailsDao.insertContentDetails(contentDetails);
        }

        // 필요한 경우 contentDetails에 infoname, infotext 등을 저장하는 로직을 추가
        // 하지만 `content_details` 테이블을 수정하지 않기로 하였으므로 여기서는 저장하지 않습니다.
    }

    @Override
    public TourCourseSummaryResponse getTourCourses(TourCourseListRequest request) throws Exception {
        int page = request.getPage();
        int pageSize = request.getPageSize();
        int offset = (page - 1) * pageSize;

        // 총 아이템 수 조회
        int totalItems = areaBasedContentsDao.countTourCourses(request.getAreaCode(), request.getSigunguCode());

        // 코스 목록 조회
        List<AreaBasedContents> courses = areaBasedContentsDao.findTourCourses(
                request.getAreaCode(),
                request.getSigunguCode(),
                pageSize,
                offset
        );

        // 응답 데이터 매핑
        List<TourCourseSummaryResponse.TourCourseSummary> courseSummaries = new ArrayList<>();
        for (AreaBasedContents course : courses) {
            TourCourseSummaryResponse.TourCourseSummary summary = new TourCourseSummaryResponse.TourCourseSummary();
            summary.setContentId(course.getContentid());
            summary.setTitle(course.getTitle());
            summary.setCat3(course.getCat3());
            summary.setFirstImage(course.getFirstimage());
            summary.setFirstImage2(course.getFirstimage2());
            summary.setAreaCode(course.getAreacode());

            // 지역 이름 조회
            AreaCodes areaCode = areaCodesDao.findByAreaCode(course.getAreacode());
            if (areaCode != null) {
                summary.setAreaName(areaCode.getAreaName());
            }

            // 시군구 이름 조회
            SigunguCodes sigunguCode = sigunguCodesDao.findByAreaCodeAndSigunguCode(course.getAreacode(), course.getSigungucode());
            if (sigunguCode != null) {
                summary.setSigunguCode(sigunguCode.getSigunguCode());
                summary.setSigunguName(sigunguCode.getSigunguName());
            }

            // 포함된 여행지의 이름 목록 조회
            List<CourseDetail> courseDetails = courseDetailDao.findByContentId(course.getContentid());
            List<String> destinations = new ArrayList<>();
            if (courseDetails != null && !courseDetails.isEmpty()) {
                for (CourseDetail detail : courseDetails) {
                    destinations.add(detail.getSubName());
                }
            } else {
                // API를 통해 데이터 가져오기
                courseDetails = fetchCourseDetailsFromAPI(course.getContentid());
                if (courseDetails != null && !courseDetails.isEmpty()) {
                    // 기존 데이터 삭제 후 삽입
                    courseDetailDao.deleteByContentId(course.getContentid());
                    courseDetailDao.insertCourseDetails(courseDetails);
                    for (CourseDetail detail : courseDetails) {
                        destinations.add(detail.getSubName());
                    }
                }
            }
            summary.setDestinations(destinations);

            courseSummaries.add(summary);
        }

        int totalPages = (int) Math.ceil((double) totalItems / pageSize);

        TourCourseSummaryResponse response = new TourCourseSummaryResponse();
        response.setCourses(courseSummaries);
        response.setTotalItems(totalItems);
        response.setTotalPages(totalPages);
        response.setCurrentPage(page);

        return response;
    }
}
