package com.bho.catchtrippingbackend.board.dto;

import com.bho.catchtrippingbackend.board.entity.Board;
import com.bho.catchtrippingbackend.board.entity.BoardLike;
import com.bho.catchtrippingbackend.user.entity.User;

public record BoardLikeRequestDto(
        Long boardId
) {
    public BoardLike toEntity (User user, Board board) {
        return BoardLike.builder()
                .user(user)
                .board(board)
                .build();
    }
}
