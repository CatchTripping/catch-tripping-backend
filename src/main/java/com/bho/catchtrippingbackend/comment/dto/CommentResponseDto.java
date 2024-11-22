package com.bho.catchtrippingbackend.comment.dto;

import com.bho.catchtrippingbackend.comment.entity.Comment;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public record CommentResponseDto(
        Long commentId,
        Long userId,
        String content,
        boolean deleted,
        int depth,
        String createdDate,
        String createdAt,
        String updatedDate,
        String updatedAt
) {
    public static CommentResponseDto from (Comment comment) {
        return new CommentResponseDto(
                comment.getId(),
                comment.getUser().getUserId(),
                comment.getContent(),
                comment.isDeleted(),
                comment.getDepth(),
                comment.getCreatedAt().format(DateTimeFormatter.ofPattern("yy년 MM월 dd일")),
                comment.getCreatedAt().format(DateTimeFormatter.ofPattern("HH:mm")),
                comment.getUpdatedAt().format(DateTimeFormatter.ofPattern("yy년 MM월 dd일")),
                comment.getUpdatedAt().format(DateTimeFormatter.ofPattern("HH:mm"))
        );
    }

    public static CommentResponseDto fromDeleted(Comment comment) {
        return new CommentResponseDto(
                comment.getId(),
                null,
                null,
                true,
                comment.getDepth(),
                null,
                null,
                null,
                null
        );
    }

    public static CommentResponseDto fromComment(Comment comment) {
        if (comment.isDeleted()) {
            return fromDeleted(comment);
        } else {
            return from(comment);
        }
    }
}