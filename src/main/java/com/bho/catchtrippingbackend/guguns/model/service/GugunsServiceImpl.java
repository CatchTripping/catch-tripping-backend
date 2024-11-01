package com.bho.catchtrippingbackend.guguns.model.service;

import com.bho.catchtrippingbackend.guguns.model.GugunsDto;
import com.bho.catchtrippingbackend.guguns.model.dao.GugunsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.SQLException;
import java.util.List;

@Service
public class GugunsServiceImpl implements GugunsService {

    private final GugunsDao gugunsDao;

    @Autowired
    public GugunsServiceImpl(GugunsDao gugunsDao) {
        this.gugunsDao = gugunsDao;
    }

    @Override
    public List<GugunsDto> listGuguns(int sidoCode) throws SQLException {
        return this.gugunsDao.listGuguns(sidoCode);
    }
}