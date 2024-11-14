package com.bho.catchtrippingbackend.board.dto;

import com.bho.catchtrippingbackend.board.entity.Board;

import java.time.format.DateTimeFormatter;

public record BoardDetailDTO(
        Long boardId,
        String userName,
        String content,
        String createdDate,
        String createdAt
) {
    public static BoardDetailDTO from(Board board) {
        return new BoardDetailDTO(
                board.getId(),
                board.getUser().getUserName(),
                board.getContent(),
                board.getCreatedAt().format(DateTimeFormatter.ofPattern("yy년 MM월 dd일")),
                board.getCreatedAt().format(DateTimeFormatter.ofPattern("HH:mm"))
        );
    }
}
