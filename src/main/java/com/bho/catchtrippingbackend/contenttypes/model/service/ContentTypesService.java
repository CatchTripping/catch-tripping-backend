package com.bho.catchtrippingbackend.contenttypes.model.service;

import com.bho.catchtrippingbackend.contenttypes.model.ContentTypesDto;
import java.sql.SQLException;
import java.util.List;

public interface ContentTypesService {
    List<ContentTypesDto> listContentTypes() throws SQLException;
}
