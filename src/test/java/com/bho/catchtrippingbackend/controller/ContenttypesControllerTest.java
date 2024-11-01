package com.bho.catchtrippingbackend.controller;

import com.bho.catchtrippingbackend.contenttypes.model.ContentTypesDto;
import com.bho.catchtrippingbackend.contenttypes.model.service.ContentTypesService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;
import java.util.List;

@SpringBootTest
public class ContenttypesControllerTest {

    @Autowired
    ContentTypesService contentTypesService;

    @Test
    void test() throws SQLException {
        List<ContentTypesDto> list = contentTypesService.listContentTypes(); // 서울
        for (ContentTypesDto contenttype : list) {
            System.out.println(contenttype.toString());
        }
    }
}
