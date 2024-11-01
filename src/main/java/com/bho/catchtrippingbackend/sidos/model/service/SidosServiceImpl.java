package com.bho.catchtrippingbackend.sidos.model.service;

import com.bho.catchtrippingbackend.sidos.model.SidosDto;
import com.bho.catchtrippingbackend.sidos.model.dao.SidosDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SidosServiceImpl implements SidosService{

    private final SidosDao sidosDao;

    @Autowired
    public SidosServiceImpl(SidosDao sidosDao) {
        this.sidosDao = sidosDao;
    }

    @Override
    public List<SidosDto> listSidos() {
        return sidosDao.listSidos();
    }
}
