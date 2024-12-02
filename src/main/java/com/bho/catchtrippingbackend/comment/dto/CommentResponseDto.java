package com.bho.catchtrippingbackend.comment.dto;

import com.bho.catchtrippingbackend.comment.entity.Comment;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public record CommentResponseDto(
        Long commentId,
        String userName,
        String profileImage,
        String content,
        boolean deleted,
        int depth,
        String createdDate,
        String createdAt,
        String updatedDate,
        String updatedAt
) {
    public static CommentResponseDto from (Comment comment, String profileImage) {
        return new CommentResponseDto(
                comment.getId(),
                comment.getUser().getUserName(),
                profileImage,
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
                null,
                true,
                comment.getDepth(),
                null,
                null,
                null,
                null
        );
    }

    public static CommentResponseDto fromComment(Comment comment, String profileImage) {
        if (comment.isDeleted()) {
            return fromDeleted(comment);
        } else {
            return from(comment, profileImage);
        }
    }
}
