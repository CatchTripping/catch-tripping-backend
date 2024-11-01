package com.bho.catchtrippingbackend.controller;

import com.bho.catchtrippingbackend.attractions.model.AttractionsDto;
import com.bho.catchtrippingbackend.attractions.model.service.AttractionsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;
import java.util.List;

@SpringBootTest
public class AttractionsControllerTest {

    @Autowired
    AttractionsService attractionsService;

    @Test
    void test() throws SQLException {
        List<AttractionsDto> list = attractionsService.listAttractions(1, 1, 1, "강남", 1); // 서울
        for (AttractionsDto attraction : list) {
            System.out.println(attraction.toString());
        }
    }
}
