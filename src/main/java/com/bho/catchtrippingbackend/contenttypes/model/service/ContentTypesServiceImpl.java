package com.bho.catchtrippingbackend.contenttypes.model.service;

import com.bho.catchtrippingbackend.contenttypes.model.ContentTypesDto;
import com.bho.catchtrippingbackend.contenttypes.model.dao.ContentTypesDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.SQLException;
import java.util.List;

@Service
public class ContentTypesServiceImpl implements ContentTypesService {

    public ContentTypesDao contentTypesDao;

    @Autowired
    public ContentTypesServiceImpl(ContentTypesDao contentTypesDao) {
        this.contentTypesDao = contentTypesDao;
    }

    @Override
    public List<ContentTypesDto> listContentTypes() throws SQLException {
        return this.contentTypesDao.listContentTypes();
    }
}
