package com.bho.catchtrippingbackend.guguns.model.service;

import com.bho.catchtrippingbackend.guguns.model.GugunsDto;
import java.sql.SQLException;
import java.util.List;

public interface GugunsService {
    List<GugunsDto> listGuguns(int sidoCode) throws SQLException;
}
