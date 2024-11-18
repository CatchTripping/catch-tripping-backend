package com.bho.catchtrippingbackend.board.entity;

import com.bho.catchtrippingbackend.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class BoardLike {
    private Long id;
    private User user;
    private Board board;
    private LocalDateTime createdAt;

    @Builder
    public BoardLike(Long id, User user, Board board, LocalDateTime createdAt) {
        this.id = id;
        this.user = user;
        this.board = board;
        this.createdAt = createdAt;
    }
}
