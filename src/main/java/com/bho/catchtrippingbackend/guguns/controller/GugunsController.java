package com.bho.catchtrippingbackend.guguns.controller;

import com.bho.catchtrippingbackend.guguns.model.GugunsDto;
import com.bho.catchtrippingbackend.guguns.model.service.GugunsService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/guguns")
public class GugunsController {

    private final GugunsService gugunsService;

    @Autowired
    public GugunsController(GugunsService gugunsService) {this.gugunsService = gugunsService;}

    @GetMapping("/list")
    public List<GugunsDto> listGuguns(@RequestParam int sidoCode, HttpSession session) {
//        MemberDto memberDto = (MemberDto) session.getAttribute("userinfo");
//        if (memberDto == null) {
//            throw new RuntimeException("로그인이 필요합니다.");
//        }
        try {
            return gugunsService.listGuguns(sidoCode);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}