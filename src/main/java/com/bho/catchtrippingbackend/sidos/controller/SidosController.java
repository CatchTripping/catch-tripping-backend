package com.bho.catchtrippingbackend.sidos.controller;

import com.bho.catchtrippingbackend.sidos.model.SidosDto;
import com.bho.catchtrippingbackend.sidos.model.service.SidosService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/sidos")
public class SidosController {

    private final SidosService sidosService;

    @Autowired
    public SidosController(SidosService sidosService) {
        this.sidosService = sidosService;
    }

    @GetMapping("/list")
    public List<SidosDto> listSidos(HttpSession session) {
//        MemberDto memberDto = (MemberDto) session.getAttribute("userinfo");
//        if (memberDto == null) {
//            throw new RuntimeException("로그인이 필요합니다.");
//        }
        try {
            return sidosService.listSidos();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
