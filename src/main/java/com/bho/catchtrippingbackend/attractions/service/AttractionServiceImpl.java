package com.bho.catchtrippingbackend.attractions.service;

import com.bho.catchtrippingbackend.attractions.dao.*;
import com.bho.catchtrippingbackend.attractions.dto.*;
import com.bho.catchtrippingbackend.attractions.dto.request.HotPlaceRequest;
import com.bho.catchtrippingbackend.attractions.dto.response.HotPlaceResponse;
import com.bho.catchtrippingbackend.attractions.service.AttractionService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AttractionServiceImpl implements AttractionService {

    private final AreaBasedContentsDao areaBasedContentsDao;
    private final AreaCodesDao areaCodesDao;
    private final SigunguCodesDao sigunguCodesDao;
    private final CategoryCodesDao categoryCodesDao;
    private final ContentTypesDao contentTypesDao;
    private final ContentDetailsDao contentDetailsDao;

    // 설정 파일에서 API 키와 베이스 URL을 가져옵니다.
    @Value("${tourapi.serviceKey}")
    private String serviceKey;

    @Value("${tourapi.baseUrl}")
    private String baseUrl;

    @Autowired
    public AttractionServiceImpl(AreaBasedContentsDao areaBasedContentsDao,
                                 AreaCodesDao areaCodesDao,
                                 SigunguCodesDao sigunguCodesDao,
                                 CategoryCodesDao categoryCodesDao,
                                 ContentTypesDao contentTypesDao, ContentDetailsDao contentDetailsDao) {
        this.areaBasedContentsDao = areaBasedContentsDao;
        this.areaCodesDao = areaCodesDao;
        this.sigunguCodesDao = sigunguCodesDao;
        this.categoryCodesDao = categoryCodesDao;
        this.contentTypesDao = contentTypesDao;
        this.contentDetailsDao=contentDetailsDao;
    }

    // AreaBasedContents 관련 메서드 구현

    @Override
    public AreaBasedContents getAttractionById(int contentId) {
        return areaBasedContentsDao.findById(contentId);
    }

    @Override
    public List<AreaBasedContents> getAllAttractions() {
        return areaBasedContentsDao.findAll();
    }

    @Override
    public List<AreaBasedContents> getAttractionsWithPagination(int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        return areaBasedContentsDao.findAllWithPagination(pageSize, offset);
    }

    @Override
    public int getTotalAttractionCount() {
        return areaBasedContentsDao.countAll();
    }

    @Override
    public List<AreaBasedContents> getAttractionsByAreaCode(int areaCode) {
        return areaBasedContentsDao.findByAreaCode(areaCode);
    }

    @Override
    public List<AreaBasedContents> getAttractionsBySigunguCode(int sigunguCode) {
        return areaBasedContentsDao.findBySigunguCode(sigunguCode);
    }

    @Override
    public List<AreaBasedContents> getAttractionsByAreaAndSigungu(int areaCode, int sigunguCode) {
        return areaBasedContentsDao.findByAreaCodeAndSigunguCode(areaCode, sigunguCode);
    }

    @Override
    public List<AreaBasedContents> getAttractionsByCategories(String cat1, String cat2, String cat3) {
        return areaBasedContentsDao.findByCategories(cat1, cat2, cat3);
    }

    @Override
    public List<AreaBasedContents> searchAttractionsByTitle(String title) {
        return areaBasedContentsDao.findByTitleContaining(title);
    }

    @Override
    public List<AreaBasedContents> getAttractionsByCoordinates(double minX, double maxX, double minY, double maxY) {
        return areaBasedContentsDao.findByMapCoordinates(minX, maxX, minY, maxY);
    }

    @Override
    public List<AreaBasedContents> getAttractionsByCreatedTime(LocalDateTime start, LocalDateTime end) {
        return areaBasedContentsDao.findByCreatedTimeBetween(start, end);
    }

    @Override
    public List<AreaBasedContents> getAttractionsByModifiedTime(LocalDateTime start, LocalDateTime end) {
        return areaBasedContentsDao.findByModifiedTimeBetween(start, end);
    }

    @Override
    public List<AreaBasedContents> searchAttractions(AttractionSearchCriteria criteria) {
        return areaBasedContentsDao.searchAttractions(criteria);
    }

    @Override
    public List<AreaBasedContents> searchAttractionsWithPagination(AttractionSearchCriteria criteria, int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        return areaBasedContentsDao.searchAttractionsWithPagination(criteria, pageSize, offset);
    }

    @Override
    public int getTotalAttractionCount(AttractionSearchCriteria criteria) {
        return areaBasedContentsDao.countSearchAttractions(criteria);
    }

    // AreaCodes 관련 메서드 구현

    @Override
    public AreaCodes getAreaCode(int areaCode) {
        return areaCodesDao.findByAreaCode(areaCode);
    }

    @Override
    public List<AreaCodes> getAllAreaCodes() {
        return areaCodesDao.findAll();
    }

    @Override
    public List<AreaCodes> getAreaCodesByName(String areaName) {
        return areaCodesDao.findByAreaName(areaName);
    }

    @Override
    public List<AreaCodes> searchAreaCodesByName(String partialName) {
        return areaCodesDao.findByAreaNameContaining(partialName);
    }

    // SigunguCodes 관련 메서드 구현

    @Override
    public SigunguCodes getSigunguCode(int areaCode, int sigunguCode) {
        return sigunguCodesDao.findByAreaCodeAndSigunguCode(areaCode, sigunguCode);
    }

    @Override
    public List<SigunguCodes> getSigunguCodesByAreaCode(int areaCode) {
        return sigunguCodesDao.findByAreaCode(areaCode);
    }

    @Override
    public List<SigunguCodes> getAllSigunguCodes() {
        return sigunguCodesDao.findAll();
    }

    @Override
    public List<SigunguCodes> getSigunguCodesByName(String sigunguName) {
        return sigunguCodesDao.findBySigunguName(sigunguName);
    }

    @Override
    public List<SigunguCodes> searchSigunguCodesByName(String partialName) {
        return sigunguCodesDao.findBySigunguNameContaining(partialName);
    }

    // CategoryCodes 관련 메서드 구현

    @Override
    public CategoryCodes getCategoryCode(String categoryCode) {
        return categoryCodesDao.findByCategoryCode(categoryCode);
    }

    @Override
    public List<CategoryCodes> getAllCategoryCodes() {
        return categoryCodesDao.findAll();
    }

    @Override
    public List<CategoryCodes> getCategoryCodesByName(String categoryName) {
        return categoryCodesDao.findByCategoryName(categoryName);
    }

    @Override
    public List<CategoryCodes> searchCategoryCodesByName(String partialName) {
        return categoryCodesDao.findByCategoryNameContaining(partialName);
    }

    // ContentTypes 관련 메서드 구현

    @Override
    public ContentTypes getContentType(int contentTypeId) {
        return contentTypesDao.findByContentTypeId(contentTypeId);
    }

    @Override
    public List<ContentTypes> getAllContentTypes() {
        return contentTypesDao.findAll();
    }

    @Override
    public List<ContentTypes> getContentTypesByName(String contentTypeName) {
        return contentTypesDao.findByContentTypeName(contentTypeName);
    }

    @Override
    public List<ContentTypes> searchContentTypesByName(String partialName) {
        return contentTypesDao.findByContentTypeNameContaining(partialName);
    }

    @Override
    public List<HotPlaceResponse> getHotPlaces(HotPlaceRequest request) {
        int limit = request.getOffset();
        int offset = (request.getPage() - 1) * limit;
        return areaBasedContentsDao.findHotPlaces(
                request.getHotPlaceType(),
                request.getRegionCode(),
                request.getSigunguCodes(),
                limit,
                offset
        );
    }

    @Override
    public ContentDetails getContentDetails(int contentId) throws Exception {
        // 데이터베이스에서 콘텐츠 상세 정보 확인
        ContentDetails contentDetails = contentDetailsDao.findByContentId(contentId);

        if (contentDetails != null) {
            System.out.println("DB에 존재");
            return contentDetails;
        } else {
            // OpenAPI에서 가져오기
            contentDetails = fetchContentDetailsFromAPI(contentId);

            if (contentDetails != null) {
                // 데이터베이스에 저장
                contentDetailsDao.insertContentDetails(contentDetails);
                return contentDetails;
            } else {
                throw new Exception("API에서 콘텐츠 상세 정보를 가져오지 못했습니다.");
            }
        }
    }

    private ContentDetails fetchContentDetailsFromAPI(int contentId) throws Exception {
        RestTemplate restTemplate = new RestTemplate();

        // 필요한 파라미터로 URL 빌드
        URI url = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("serviceKey", serviceKey)
                .queryParam("MobileOS", "WIN")
                .queryParam("MobileApp", "catchtripping")
                .queryParam("contentId", contentId)
                .queryParam("defaultYN", "Y")
                .queryParam("firstImageYN", "Y")
                .queryParam("areacodeYN", "Y")
                .queryParam("catcodeYN", "Y")
                .queryParam("addrinfoYN", "Y")
                .queryParam("mapinfoYN", "Y")
                .queryParam("overviewYN", "Y")
                .queryParam("_type", "json") // JSON 응답을 받기 위해
                .build(true) // 인코딩을 여기서 처리하므로 false로 설정
                .toUri();

        // URL 출력하여 확인
        System.out.println("요청 URL: " + url);

        // API 호출
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            // JSON 응답 파싱
            String responseBody = response.getBody();

            // API 응답 출력하여 확인
            System.out.println("API 응답: " + responseBody);

            // JSON 파싱하여 필요한 필드 추출
            ContentDetails contentDetails = parseContentDetailsFromJson(responseBody, contentId);

            System.out.println("출력");
            System.out.println(contentDetails);
            return contentDetails;
        } else {
            throw new Exception("API 호출 실패: 상태 코드 " + response.getStatusCode());
        }
    }

    private ContentDetails parseContentDetailsFromJson(String json, int contentId) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        // 응답 전체를 출력하여 확인
        System.out.println("API 응답: " + json);

        JsonNode rootNode = objectMapper.readTree(json);
        JsonNode responseNode = rootNode.path("response");
        JsonNode headerNode = responseNode.path("header");
        String resultCode = headerNode.path("resultCode").asText();

        if (!"0000".equals(resultCode)) {
            String resultMsg = headerNode.path("resultMsg").asText();
            throw new Exception("API 에러: " + resultMsg);
        }

        JsonNode bodyNode = responseNode.path("body");
        JsonNode itemsNode = bodyNode.path("items");
        JsonNode itemNode = itemsNode.path("item");

        // **item이 배열인지 객체인지 확인**
        if (itemNode.isArray()) {
            if (!itemNode.isEmpty()) {
                itemNode = itemNode.get(0); // 첫 번째 아이템 사용
            } else {
                throw new Exception("API 응답에 item이 없습니다.");
            }
        }

        String overview = itemNode.path("overview").asText();
        String homepage = itemNode.path("homepage").asText();

        // ContentDetails 객체 생성
        ContentDetails contentDetails = new ContentDetails();
        contentDetails.setContentId(contentId);
        contentDetails.setOverview(overview);
        contentDetails.setHomepage(homepage);

        return contentDetails;
    }
}
