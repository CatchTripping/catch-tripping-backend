package com.bho.catchtrippingbackend.board.service;

import com.amazonaws.HttpMethod;
import com.bho.catchtrippingbackend.board.dao.BoardImageDao;
import com.bho.catchtrippingbackend.board.dao.LikedBoardDao;
import com.bho.catchtrippingbackend.board.dao.MyBoardDao;
import com.bho.catchtrippingbackend.board.dto.BoardDetailDto;
import com.bho.catchtrippingbackend.board.entity.Board;
import com.bho.catchtrippingbackend.board.entity.BoardLike;
import com.bho.catchtrippingbackend.comment.dao.CommentDao;
import com.bho.catchtrippingbackend.comment.entity.Comment;
import com.bho.catchtrippingbackend.s3.service.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class LikedBoardService {
    private final LikedBoardDao likedBoardDao;
    private final CommentDao commentDao;
    private final BoardImageDao boardImageDao;
    private final S3Service s3Service;

    @Transactional(readOnly = true)
    public List<BoardDetailDto> findLikedBoardsWithPaging(Long userId, Long cursor, int size) {
        List<Board> boards = likedBoardDao.findLikedBoardsWithPaging(userId, cursor, size);

        if (boards.isEmpty()) {
            return List.of(); // 빈 리스트 반환
        }

        // board 가져오기
        List<Long> boardIds = boards.stream()
                .map(Board::getId)
                .collect(Collectors.toList());

        // 댓글 가져오기
        List<Comment> comments = commentDao.findCommentsByBoardIds(boardIds);

        //이미지 가져오기
        List<BoardLike> images = boardImageDao.findCommentsByBoardIds(boardIds);

        // Board에 댓글과 이미지 매핑
        Map<Long, Long> commentCountsByBoardId = comments.stream()
                .collect(Collectors.groupingBy(comment -> comment.getBoard().getId(), Collectors.counting()));

        Map<Long, List<String>> imagesByBoardId = images.stream()
                .collect(Collectors.groupingBy(Image::getBoardId));

        // Step 5: Board를 DTO로 변환
        return boards.stream()
                .map(board -> convertToBoardDetailDto(board, commentsByBoardId.getOrDefault(board.getId(), List.of()),
                        imagesByBoardId.getOrDefault(board.getId(), List.of())))
                .collect(Collectors.toList());
    }


    private BoardDetailDto convertToBoardDetailDto(Board board, List<Comment> comments, List<String> imageUrls) {
        String profileImage = s3Service.generatePresignedUrl(board.getUser().getProfileImage(), HttpMethod.GET);
        List<String> presignedImageUrls = s3Service.generatePresignedUrls(imageUrls, HttpMethod.GET);

        return BoardDetailDto.from(
                board,
                profileImage,
                presignedImageUrls
        );
    }
//    private BoardDetailDto convertToBoardDetailDto(Board board) {
//        String profileImage = s3Service.generatePresignedUrl(board.getUser().getProfileImage(), HttpMethod.GET);
//        List<String> imageUrls = s3Service.generatePresignedUrls(board.getImageUrls(), HttpMethod.GET);
//        return BoardDetailDto.from(board, profileImage, imageUrls);
//    }
}
