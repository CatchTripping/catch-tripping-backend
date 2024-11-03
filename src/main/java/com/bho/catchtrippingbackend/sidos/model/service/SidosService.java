package com.bho.catchtrippingbackend.sidos.model.service;

import com.bho.catchtrippingbackend.sidos.model.SidosDto;
import com.bho.catchtrippingbackend.sidos.model.dao.SidosDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SidosService {
    private final SidosDao sidosDao;

    public List<SidosDto> listSidos() {
        return sidosDao.listSidos();
    }
}
