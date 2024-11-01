package com.bho.catchtrippingbackend.controller;

import com.bho.catchtrippingbackend.sidos.model.SidosDto;
import com.bho.catchtrippingbackend.sidos.model.service.SidosService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;
import java.util.List;

@SpringBootTest
public class SidosControllerTest {

    @Autowired
    SidosService sidosService;

    @Test
    void test() throws SQLException {
        List<SidosDto> list = sidosService.listSidos();
        for (SidosDto sido : list) {
            System.out.println(sido.toString());
        }
    }
}
