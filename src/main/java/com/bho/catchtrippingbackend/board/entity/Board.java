package com.bho.catchtrippingbackend.board.entity;

import com.bho.catchtrippingbackend.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class Board {

    private Long id;
    private User user;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public Board(Long id, User user, String content, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.user = user;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }


    public void update(String content) {
        this.content = content;
        this.updatedAt = LocalDateTime.now();
    }
}
