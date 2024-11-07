package com.bho.catchtrippingbackend.sidos.model.dao;

import com.bho.catchtrippingbackend.sidos.model.SidosDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SidosDao {
    @Select("SELECT no, sido_code AS sidoCode, sido_name AS sidoName FROM sidos")
    List<SidosDto> listSidos();
}