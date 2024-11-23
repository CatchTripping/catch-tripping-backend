package com.bho.catchtrippingbackend.board.service;

import com.amazonaws.HttpMethod;
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
public class MyBoardService {

    private final MyBoardDao myBoardDao;
    private final S3Service s3Service;

    @Transactional(readOnly = true)
    public List<BoardDetailDto> findMyBoardsWithPaging(Long userId, int page, int size) {
        int offset = (page - 1) * size;
        log.info("offset : {}", offset);

        List<Board> boards = myBoardDao.findMyBoardsWithPaging(userId, offset, size);

        return boards.stream()
                .map(this::convertToBoardDetailDto)
                .collect(Collectors.toList());
    }

    private BoardDetailDto convertToBoardDetailDto(Board board) {
        List<String> imageUrls = s3Service.generatePresignedUrls(board.getImageUrls(), HttpMethod.GET);
        return BoardDetailDto.from(board, imageUrls);
    }

}
