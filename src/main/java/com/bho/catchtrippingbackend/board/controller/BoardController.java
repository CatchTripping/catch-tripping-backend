package com.bho.catchtrippingbackend.board.controller;

import com.bho.catchtrippingbackend.board.dto.BoardDetailDto;
import com.bho.catchtrippingbackend.board.dto.BoardSaveRequestDto;
import com.bho.catchtrippingbackend.board.dto.BoardUpdateRequestDto;
import com.bho.catchtrippingbackend.board.service.BoardService;
import com.bho.catchtrippingbackend.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<String> save(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody BoardSaveRequestDto requestDTO) {
        boardService.save(userDetails, requestDTO);

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
        BoardDetailDto boardDetailDto = boardService.update(userDetails, boardId, requestDTO);
        return ResponseEntity.ok(boardDetailDto);
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<String> deleteBoard(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable("boardId") Long boardId) {
        boardService.delete(userDetails, boardId);
        // return status 수정
        return ResponseEntity.ok("삭제 완료");
    }
}
