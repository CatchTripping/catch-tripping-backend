package com.bho.catchtrippingbackend.comment.entity;

import com.bho.catchtrippingbackend.board.entity.Board;
import com.bho.catchtrippingbackend.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
public class Comment {
    private Long id;
    private User user;
    private Board board;
    private String content;
    private Comment parentComment;
    private boolean deleted;
    private int depth;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public Comment(Long id, User user, Board board, String content, Comment parentComment, int depth, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.user = user;
        this.board = board;
        this.content = content;
        this.parentComment = parentComment;
        this.depth = depth;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void update(String content) {
        this.content = content;
        this.updatedAt = LocalDateTime.now();
    }

    public void delete() {
        this.deleted = true;
        this.updatedAt = LocalDateTime.now();
    }
}
