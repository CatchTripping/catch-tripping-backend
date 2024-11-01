package com.bho.catchtrippingbackend.attractions.model.dao;

import com.bho.catchtrippingbackend.attractions.model.AttractionsDto;
import org.apache.ibatis.annotations.Mapper;
import java.sql.SQLException;
import java.util.List;

@Mapper
public interface AttractionsDao {
    List<AttractionsDto> listAttractions(int sidoCode, int gugunCode, int contentTypeId, String title, int pgno) throws SQLException;
}
