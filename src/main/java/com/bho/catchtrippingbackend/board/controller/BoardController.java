package com.bho.catchtrippingbackend.board.controller;

import com.bho.catchtrippingbackend.board.dto.BoardDetailDto;
import com.bho.catchtrippingbackend.board.dto.BoardSaveRequestDto;
import com.bho.catchtrippingbackend.board.dto.BoardUpdateRequestDto;
import com.bho.catchtrippingbackend.board.service.BoardService;
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
    public ResponseEntity<String> saveBoard(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody BoardSaveRequestDto requestDTO) {
        boardService.save(userDetails, requestDTO);

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
            @RequestBody BoardUpdateRequestDto requestDTO) {
        BoardDetailDto boardDetailDto = boardService.update(userDetails, boardId, requestDTO);
        return ResponseEntity.ok(boardDetailDto);
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<String> deleteBoard(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long boardId) {
        boardService.delete(userDetails, boardId);
        // return status 수정
        return ResponseEntity.ok("삭제 완료");
    }
}
