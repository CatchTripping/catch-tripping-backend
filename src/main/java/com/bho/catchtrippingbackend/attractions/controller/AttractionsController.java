package com.bho.catchtrippingbackend.attractions.controller;

import com.bho.catchtrippingbackend.attractions.model.AttractionsDto;
import com.bho.catchtrippingbackend.attractions.model.service.AttractionsService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/attractions")
public class AttractionsController {

    private AttractionsService attractionsService;

    @Autowired
    public AttractionsController(AttractionsService attractionsService) { this.attractionsService = attractionsService;}

    @GetMapping("/list")
    public List<AttractionsDto> listAttractions(@RequestParam int sidoCode, @RequestParam int gugunCode, @RequestParam int contentTypeId, @RequestParam String title, @RequestParam int pgno, HttpSession session) {
//        MemberDto memberDto = (MemberDto) session.getAttribute("userinfo");
//        if (memberDto == null) {
//            throw new RuntimeException("로그인이 필요합니다.");
//        }
        try {
            return attractionsService.listAttractions(sidoCode, gugunCode, contentTypeId, title, pgno);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
