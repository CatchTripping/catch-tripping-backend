package com.bho.catchtrippingbackend.attractions.model.service;

import com.bho.catchtrippingbackend.attractions.model.AttractionsDto;
import java.sql.SQLException;
import java.util.List;

public interface AttractionsService {
    List<AttractionsDto> listAttractions(int sidoCode, int gugunCode, int contentTypeId, String title, int pgno) throws SQLException;
}
