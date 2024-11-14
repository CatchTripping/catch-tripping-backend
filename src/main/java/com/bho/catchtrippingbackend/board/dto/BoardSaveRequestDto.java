package com.bho.catchtrippingbackend.board.dto;

import com.bho.catchtrippingbackend.board.entity.Board;
import com.bho.catchtrippingbackend.user.entity.User;

public record BoardSaveRequestDto (
        String content
) {
    public Board from (User user) {
        return Board.builder()
                .user(user)
                .content(content)
                .build();
    }
}