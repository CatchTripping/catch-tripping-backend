package com.bho.catchtrippingbackend.contenttypes.controller;

import com.bho.catchtrippingbackend.contenttypes.model.ContentTypesDto;
import com.bho.catchtrippingbackend.contenttypes.model.service.ContentTypesService;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/contenttypes")
public class ContentTypesController {

    private ContentTypesService contentTypesService;

    public ContentTypesController(ContentTypesService contentTypesService) {this.contentTypesService = contentTypesService;}

    @GetMapping("/list")
    public List<ContentTypesDto> listContentTypes(HttpSession session) {
//        MemberDto memberDto = (MemberDto) session.getAttribute("userinfo");
//        if (memberDto == null) {
//            throw new RuntimeException("로그인이 필요합니다.");
//        }
        try {
            return contentTypesService.listContentTypes();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
