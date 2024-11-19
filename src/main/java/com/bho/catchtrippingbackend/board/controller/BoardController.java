package com.bho.catchtrippingbackend.board.controller;

import com.bho.catchtrippingbackend.board.dto.*;
import com.bho.catchtrippingbackend.board.service.BoardService;
import com.bho.catchtrippingbackend.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<String> save(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody BoardSaveRequestDto requestDTO) {
        boardService.save(userDetails.getUserId(), requestDTO);

        return ResponseEntity.ok("저장 완료");
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<BoardDetailDto> findBoardById(
            @PathVariable("boardId") Long boardId) {
        BoardDetailDto boardDetailDto = boardService.findBoardDetailById(boardId);

        return ResponseEntity.ok(boardDetailDto);
    }

    @PatchMapping("/{boardId}")
    public ResponseEntity<BoardDetailDto> updateBoard(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable("boardId") Long boardId,
            @RequestBody BoardUpdateRequestDto requestDTO) {
        BoardDetailDto boardDetailDto = boardService.update(userDetails.getUserId(), boardId, requestDTO);
        return ResponseEntity.ok(boardDetailDto);
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<String> deleteBoard(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable("boardId") Long boardId) {
        boardService.delete(userDetails.getUserId(), boardId);

        return ResponseEntity.ok("삭제 완료");
    }

    @PostMapping("/like")
    public ResponseEntity<String> addLike(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody BoardLikeRequestDto requestDto) {
        boardService.addLike(userDetails.getUserId(), requestDto);

        return ResponseEntity.ok("좋아요 추가 완료");
    }

    @DeleteMapping("/like")
    public ResponseEntity<String> deleteLike(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody BoardLikeRequestDto requestDto) {
        boardService.deleteLike(userDetails.getUserId(), requestDto);

        return ResponseEntity.ok("좋아요 삭제 완료");
    }

    @GetMapping
    public ResponseEntity<List<BoardPreviewDto>> findBoardsWithPaging(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "20") int size) {
        List<BoardPreviewDto> boards = boardService.findBoardsWithPaging(page, size);
        return ResponseEntity.ok(boards);
    }
}
