package com.bho.catchtrippingbackend.contenttypes.model.dao;

import com.bho.catchtrippingbackend.contenttypes.model.ContentTypesDto;
import org.apache.ibatis.annotations.Mapper;
import java.sql.SQLException;
import java.util.List;

@Mapper
public interface ContentTypesDao {
    List<ContentTypesDto> listContentTypes() throws SQLException;
}
