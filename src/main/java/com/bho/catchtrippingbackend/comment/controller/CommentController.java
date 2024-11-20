package com.bho.catchtrippingbackend.comment.controller;

import com.bho.catchtrippingbackend.comment.dto.CommentResponseDto;
import com.bho.catchtrippingbackend.comment.dto.CommentSaveRequestDto;
import com.bho.catchtrippingbackend.comment.dto.CommentUpdateRequestDto;
import com.bho.catchtrippingbackend.comment.service.CommentService;
import com.bho.catchtrippingbackend.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<String> save(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody CommentSaveRequestDto requestDto) {
//        CommentResponseDto responseDto = commentService.save(userDetails.getUserId(), requestDto);
        commentService.save(userDetails.getUserId(), requestDto);

        return ResponseEntity.ok("댓글 저장 성공");
    }

    @PatchMapping
    public ResponseEntity<CommentResponseDto> update(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody CommentUpdateRequestDto requestDto) {

        CommentResponseDto responseDto = commentService.update(userDetails.getUserId(), requestDto);

        return ResponseEntity.ok(responseDto);
    }

}