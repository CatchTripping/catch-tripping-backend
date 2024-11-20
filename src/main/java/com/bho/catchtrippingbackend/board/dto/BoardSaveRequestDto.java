package com.bho.catchtrippingbackend.board.dto;

import com.bho.catchtrippingbackend.board.entity.Board;
import com.bho.catchtrippingbackend.user.entity.User;

import java.util.List;

public record BoardSaveRequestDto (
        String content,
        List<String> imageKeys
) {
    public Board from (User user) {
        return Board.builder()
                .user(user)
                .content(content)
                .build();
    }
}