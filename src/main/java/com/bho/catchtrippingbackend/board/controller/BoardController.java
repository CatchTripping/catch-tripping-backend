package com.bho.catchtrippingbackend.board.controller;

import com.bho.catchtrippingbackend.board.dto.BoardDetailDTO;
import com.bho.catchtrippingbackend.board.dto.BoardSaveRequestDto;
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
    public ResponseEntity<Void> saveBoard(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody BoardSaveRequestDto requestDTO) {
        boardService.save(userDetails, requestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<BoardDetailDTO> getBoardById(
            @PathVariable Long boardId) {
        BoardDetailDTO boardDetailDTO = boardService.getBoardDetailById(boardId);

        return ResponseEntity.ok(boardDetailDTO);
    }

//    @PutMapping("/{boardId}")
//    public ResponseEntity<Void> updateBoard(
//            @AuthenticationPrincipal UserDetails userDetails,
//            @PathVariable Long boardId,
//            @RequestBody BoardSaveRequestDto requestDTO) {
//        boardService.update(userDetails, boardId, requestDTO);
//        return ResponseEntity.status(HttpStatus.OK).build();
//    }
}
