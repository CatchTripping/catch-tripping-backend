package com.bho.catchtrippingbackend.comment.controller;

import com.bho.catchtrippingbackend.comment.dto.CommentDeleteRequestDto;
import com.bho.catchtrippingbackend.comment.dto.CommentResponseDto;
import com.bho.catchtrippingbackend.comment.dto.CommentSaveRequestDto;
import com.bho.catchtrippingbackend.comment.dto.CommentUpdateRequestDto;
import com.bho.catchtrippingbackend.comment.service.CommentService;
import com.bho.catchtrippingbackend.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<String> update(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody CommentUpdateRequestDto requestDto) {

        commentService.update(userDetails.getUserId(), requestDto);

        return ResponseEntity.ok("댓글 수정 성공");
    }

    @PatchMapping("/delete")
    public ResponseEntity<String> delete(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody CommentDeleteRequestDto requestDto) {

        commentService.delete(userDetails.getUserId(), requestDto);

        return ResponseEntity.ok("댓글 삭제 성공");
    }

    @GetMapping("/{diaryId}")
    public ResponseEntity<List<CommentResponseDto>> findParentCommentsWithPaging(
            @PathVariable("diaryId") Long diaryId,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "20") int size) {

        List<CommentResponseDto> parentComments = commentService.findParentCommentsWithPaging(diaryId, page, size);

        return ResponseEntity.ok(parentComments);
    }

    @GetMapping("/child-comments/{parentId}")
    public ResponseEntity<List<CommentResponseDto>> findChildCommentsWithPaging(
            @PathVariable("parentId") Long parentId,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "20") int size) {

        List<CommentResponseDto> childComments = commentService.findChildCommentsWithPaging(parentId, page, size);

        return ResponseEntity.ok(childComments);
    }
}
