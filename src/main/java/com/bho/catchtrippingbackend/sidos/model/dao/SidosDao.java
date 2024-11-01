package com.bho.catchtrippingbackend.sidos.model.dao;

import com.bho.catchtrippingbackend.sidos.model.SidosDto;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface SidosDao {
    List<SidosDto> listSidos();
}