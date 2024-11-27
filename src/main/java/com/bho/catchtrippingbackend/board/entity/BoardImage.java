package com.bho.catchtrippingbackend.board.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class BoardImage {
    private Long id;
    private Board board;
    private String imageKey;

    @Builder
    public BoardImage(Long id, Board board, String imageKey) {
        this.id = id;
        this.board = board;
        this.imageKey = imageKey;
    }
}
