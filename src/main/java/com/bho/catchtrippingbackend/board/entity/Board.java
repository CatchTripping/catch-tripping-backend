package com.bho.catchtrippingbackend.board.entity;

import com.bho.catchtrippingbackend.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
public class Board {

    private Long id;
    private User user;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private long likeCount;

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

    public void incrementLikeCount() {
        this.likeCount += 1;
    }

    public void decrementLikeCount() {
        this.likeCount -= 1;
    }
}
