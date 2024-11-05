package com.bho.catchtrippingbackend.controller;

import com.bho.catchtrippingbackend.guguns.model.GugunsDto;
import com.bho.catchtrippingbackend.guguns.model.service.GugunsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;
import java.util.List;

@SpringBootTest
public class GugunsControllerTest {

    @Autowired
    GugunsService gugunsService;

    @Test
    void test() throws SQLException {
        List<GugunsDto> list = gugunsService.listGuguns(1); // 서울
        for (GugunsDto gugun : list) {
            System.out.println(gugun.toString());
        }
    }
}
