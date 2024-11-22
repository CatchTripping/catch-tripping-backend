package com.bho.catchtrippingbackend.board.dto;

import java.util.List;

public record BoardUpdateRequestDto(
        String content,
        List<String> imageUrls
) {}
