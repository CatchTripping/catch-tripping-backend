package com.bho.catchtrippingbackend.board.controller;

import com.bho.catchtrippingbackend.board.dto.BoardDetailDto;
import com.bho.catchtrippingbackend.board.dto.BoardLikeRequestDto;
import com.bho.catchtrippingbackend.board.dto.BoardSaveRequestDto;
import com.bho.catchtrippingbackend.board.dto.BoardUpdateRequestDto;
import com.bho.catchtrippingbackend.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<String> save(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody BoardSaveRequestDto requestDto) {
        boardService.save(userDetails, requestDto);

        return ResponseEntity.ok("저장 완료");
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<BoardDetailDto> findBoardById(
            @PathVariable Long boardId) {
        BoardDetailDto boardDetailDto = boardService.findBoardDetailById(boardId);

        return ResponseEntity.ok(boardDetailDto);
    }

    @PatchMapping("/{boardId}")
    public ResponseEntity<BoardDetailDto> updateBoard(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long boardId,
            @RequestBody BoardUpdateRequestDto requestDto) {
        BoardDetailDto boardDetailDto = boardService.update(userDetails, boardId, requestDto);

        return ResponseEntity.ok(boardDetailDto);
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<String> deleteBoard(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long boardId) {
        boardService.delete(userDetails, boardId);

        return ResponseEntity.ok("삭제 완료");
    }

    @PostMapping("/like")
    public ResponseEntity<String> addLike(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody BoardLikeRequestDto requestDto) {
        boardService.addLike(userDetails, requestDto);

        return ResponseEntity.ok("좋아요 추가 완료");
    }
}
