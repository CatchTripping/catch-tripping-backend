package com.bho.catchtrippingbackend.attractions.service;

import com.bho.catchtrippingbackend.attractions.dao.*;
import com.bho.catchtrippingbackend.attractions.dto.*;
import com.bho.catchtrippingbackend.attractions.dto.request.HotPlaceRequest;
import com.bho.catchtrippingbackend.attractions.dto.response.HotPlaceResponse;
import com.bho.catchtrippingbackend.attractions.service.AttractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AttractionServiceImpl implements AttractionService {

    private final AreaBasedContentsDao areaBasedContentsDao;
    private final AreaCodesDao areaCodesDao;
    private final SigunguCodesDao sigunguCodesDao;
    private final CategoryCodesDao categoryCodesDao;
    private final ContentTypesDao contentTypesDao;

    @Autowired
    public AttractionServiceImpl(AreaBasedContentsDao areaBasedContentsDao,
                                 AreaCodesDao areaCodesDao,
                                 SigunguCodesDao sigunguCodesDao,
                                 CategoryCodesDao categoryCodesDao,
                                 ContentTypesDao contentTypesDao) {
        this.areaBasedContentsDao = areaBasedContentsDao;
        this.areaCodesDao = areaCodesDao;
        this.sigunguCodesDao = sigunguCodesDao;
        this.categoryCodesDao = categoryCodesDao;
        this.contentTypesDao = contentTypesDao;
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
}
