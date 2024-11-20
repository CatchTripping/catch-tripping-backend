package com.bho.catchtrippingbackend.comment.dto;

public record CommentUpdateRequestDto(
        Long commentId,
        String content
) {
}
