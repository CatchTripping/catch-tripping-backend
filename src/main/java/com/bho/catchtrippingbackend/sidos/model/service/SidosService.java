package com.bho.catchtrippingbackend.sidos.model.service;

import com.bho.catchtrippingbackend.sidos.model.SidosDto;
import java.sql.SQLException;
import java.util.List;

public interface SidosService {
    List<SidosDto> listSidos() throws SQLException;
}
