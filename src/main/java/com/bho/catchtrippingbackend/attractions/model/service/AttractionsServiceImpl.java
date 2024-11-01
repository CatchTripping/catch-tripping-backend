package com.bho.catchtrippingbackend.attractions.model.service;

import com.bho.catchtrippingbackend.attractions.model.AttractionsDto;
import com.bho.catchtrippingbackend.attractions.model.dao.AttractionsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.SQLException;
import java.util.List;

@Service
public class AttractionsServiceImpl implements AttractionsService {

    private final AttractionsDao attractionsDao;

    @Autowired
    public AttractionsServiceImpl(AttractionsDao attractionsDao) {
        this.attractionsDao = attractionsDao;
    }

    @Override
    public List<AttractionsDto> listAttractions(int sidoCode, int gugunCode, int contentTypeId, String title, int pgno) throws SQLException {
        return this.attractionsDao.listAttractions(sidoCode, gugunCode, contentTypeId, title, pgno);
    }
}
