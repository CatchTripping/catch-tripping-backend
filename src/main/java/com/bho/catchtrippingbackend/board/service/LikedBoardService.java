package com.bho.catchtrippingbackend.board.service;

import com.amazonaws.HttpMethod;
import com.bho.catchtrippingbackend.board.dao.LikedBoardDao;
import com.bho.catchtrippingbackend.board.dao.MyBoardDao;
import com.bho.catchtrippingbackend.board.dto.BoardDetailDto;
import com.bho.catchtrippingbackend.board.entity.Board;
import com.bho.catchtrippingbackend.s3.service.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class LikedBoardService {
    private final LikedBoardDao likedBoardDao;
    private final S3Service s3Service;

    @Transactional(readOnly = true)
    public List<BoardDetailDto> findLikedBoardsWithPaging(Long userId, int page, int size) {
        int offset = (page - 1) * size;

        List<Board> boards = likedBoardDao.findLikedBoardsWithPaging(userId, offset, size);

        return boards.stream()
                .map(this::convertToBoardDetailDto)
                .collect(Collectors.toList());
    }

    private BoardDetailDto convertToBoardDetailDto(Board board) {
        String profileImage = s3Service.generatePresignedUrl(board.getUser().getProfileImage(), HttpMethod.GET);
        List<String> imageUrls = s3Service.generatePresignedUrls(board.getImageUrls(), HttpMethod.GET);
        return BoardDetailDto.from(board, profileImage, imageUrls);
    }
}
