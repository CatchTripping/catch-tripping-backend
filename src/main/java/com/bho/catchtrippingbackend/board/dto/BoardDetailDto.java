package com.bho.catchtrippingbackend.board.dto;

import com.bho.catchtrippingbackend.board.entity.Board;

import java.time.format.DateTimeFormatter;

public record BoardDetailDto(
        Long boardId,
        String userName,
        String content,
        String createdDate,
        String createdAt
) {
    public static BoardDetailDto from(Board board) {
        return new BoardDetailDto(
                board.getId(),
                board.getUser().getUserName(),
                board.getContent(),
                board.getCreatedAt().format(DateTimeFormatter.ofPattern("yy년 MM월 dd일")),
                board.getCreatedAt().format(DateTimeFormatter.ofPattern("HH:mm"))
        );
    }
}
