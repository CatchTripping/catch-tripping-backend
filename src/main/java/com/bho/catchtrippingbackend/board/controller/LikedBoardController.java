package com.bho.catchtrippingbackend.board.controller;

import com.bho.catchtrippingbackend.board.dto.BoardDetailDto;
import com.bho.catchtrippingbackend.board.service.LikedBoardService;
import com.bho.catchtrippingbackend.board.service.MyBoardService;
import com.bho.catchtrippingbackend.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/LikedBoard")
public class LikedBoardController {

    private final LikedBoardService likedBoardService;

    @GetMapping
    public ResponseEntity<List<BoardDetailDto>> findLikedBoardsWithPaging(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "20") int size) {
        List<BoardDetailDto> likedBoards = likedBoardService.findLikedBoardsWithPaging(userDetails.getUserId(), page, size);
        return ResponseEntity.ok(likedBoards);
    }
}
