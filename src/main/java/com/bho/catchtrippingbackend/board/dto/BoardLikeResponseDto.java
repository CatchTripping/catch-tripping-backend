package com.bho.catchtrippingbackend.board.dto;

import com.bho.catchtrippingbackend.board.entity.Board;

public record BoardLikeResponseDto(
        Long boardId,
        long likesCount
)
{
    public static BoardLikeResponseDto from(Board board) {
        return new BoardLikeResponseDto(
                board.getId(),
                board.getLikeCount()
        );
    }
}
