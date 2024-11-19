package com.bho.catchtrippingbackend.board.dto;

import com.bho.catchtrippingbackend.board.entity.Board;

import java.time.format.DateTimeFormatter;

public record BoardPreviewDto(
        Long boardId,
        String userName,
        String content,
        String createdDate,
        String createdAt,
        String updatedDate,
        String updatedAt,
        long likesCount
) {
    public static BoardPreviewDto from(Board board) {
        return new BoardPreviewDto(
                board.getId(),
                board.getUser().getUserName(),
                board.getContent(),
                board.getCreatedAt().format(DateTimeFormatter.ofPattern("yy년 MM월 dd일")),
                board.getCreatedAt().format(DateTimeFormatter.ofPattern("HH:mm")),
                board.getUpdatedAt().format(DateTimeFormatter.ofPattern("yy년 MM월 dd일")),
                board.getUpdatedAt().format(DateTimeFormatter.ofPattern("HH:mm")),
                board.getLikeCount()
        );
    }
}
