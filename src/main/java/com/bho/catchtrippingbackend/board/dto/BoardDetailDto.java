package com.bho.catchtrippingbackend.board.dto;

import com.bho.catchtrippingbackend.board.entity.Board;

import java.time.format.DateTimeFormatter;
import java.util.List;

public record BoardDetailDto(
        Long boardId,
        String userName,
        String content,
        List<String> imageUrls,
        String createdDate,
        String createdAt,
        String updatedDate,
        String updatedAt,
        long likesCount
) {
    public static BoardDetailDto from(Board board, List<String> imageUrls) {
        return new BoardDetailDto(
                board.getId(),
                board.getUser().getUserName(),
                board.getContent(),
                imageUrls,
                board.getCreatedAt().format(DateTimeFormatter.ofPattern("yy년 MM월 dd일")),
                board.getCreatedAt().format(DateTimeFormatter.ofPattern("HH:mm")),
                board.getUpdatedAt().format(DateTimeFormatter.ofPattern("yy년 MM월 dd일")),
                board.getUpdatedAt().format(DateTimeFormatter.ofPattern("HH:mm")),
                board.getLikeCount()
        );
    }
}
