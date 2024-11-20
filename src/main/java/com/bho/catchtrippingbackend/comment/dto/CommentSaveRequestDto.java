package com.bho.catchtrippingbackend.comment.dto;

import com.bho.catchtrippingbackend.board.entity.Board;
import com.bho.catchtrippingbackend.comment.entity.Comment;
import com.bho.catchtrippingbackend.user.entity.User;

public record CommentSaveRequestDto(
        Long boardId,
        String content,
        Long parentCommentId
) {
    public Comment toEntity (User user, Board board, Comment parentComment, int depth) {
        return Comment.builder()
                .user(user)
                .board(board)
                .parentComment(parentComment)
                .depth(depth)
                .build();
    }
}
