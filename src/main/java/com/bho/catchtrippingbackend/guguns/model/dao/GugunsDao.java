package com.bho.catchtrippingbackend.guguns.model.dao;

import com.bho.catchtrippingbackend.guguns.model.GugunsDto;
import org.apache.ibatis.annotations.Mapper;
import java.sql.SQLException;
import java.util.List;

@Mapper
public interface GugunsDao {
    List<GugunsDto> listGuguns(int sidoCode) throws SQLException;
}
