package com.bho.catchtrippingbackend.comment.dto;

import com.bho.catchtrippingbackend.comment.entity.Comment;

import java.time.format.DateTimeFormatter;

public record ParentCommentResponseDto(
        Long commentId,
        String userName,
        String profileImage,
        String content,
        boolean deleted,
        int depth,
        String createdDate,
        String createdAt,
        String updatedDate,
        String updatedAt,
        int childCommentCount
) {
    public static ParentCommentResponseDto from (Comment comment, String profileImage) {
        return new ParentCommentResponseDto(
                comment.getId(),
                comment.getUser().getUserName(),
                profileImage,
                comment.getContent(),
                comment.isDeleted(),
                comment.getDepth(),
                comment.getCreatedAt().format(DateTimeFormatter.ofPattern("yy년 MM월 dd일")),
                comment.getCreatedAt().format(DateTimeFormatter.ofPattern("HH:mm")),
                comment.getUpdatedAt().format(DateTimeFormatter.ofPattern("yy년 MM월 dd일")),
                comment.getUpdatedAt().format(DateTimeFormatter.ofPattern("HH:mm")),
                comment.getChildComments().size()
        );
    }

    public static ParentCommentResponseDto fromDeleted(Comment comment) {
        return new ParentCommentResponseDto(
                comment.getId(),
                null,
                null,
                null,
                true,
                comment.getDepth(),
                null,
                null,
                null,
                null,
                comment.getChildComments().size()
        );
    }

    public static ParentCommentResponseDto fromComment(Comment comment, String profileImage) {
        if (comment.isDeleted()) {
            return fromDeleted(comment);
        } else {
            return from(comment, profileImage);
        }
    }
}
