package com.bho.catchtrippingbackend.comment.service;

import com.bho.catchtrippingbackend.board.dao.BoardDao;
import com.bho.catchtrippingbackend.board.entity.Board;
import com.bho.catchtrippingbackend.comment.dao.CommentDao;
import com.bho.catchtrippingbackend.comment.dto.CommentResponseDto;
import com.bho.catchtrippingbackend.comment.dto.CommentSaveRequestDto;
import com.bho.catchtrippingbackend.comment.entity.Comment;
import com.bho.catchtrippingbackend.error.SystemException;
import com.bho.catchtrippingbackend.error.code.ClientErrorCode;
import com.bho.catchtrippingbackend.user.dao.UserDao;
import com.bho.catchtrippingbackend.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentDao commentDao;
    private final UserDao userDao;
    private final BoardDao boardDao;


    public void save(Long userId, CommentSaveRequestDto requestDto) {
        User user = getUserById(userId);
        Board board = getBoardById(requestDto.boardId());
        log.info(requestDto.toString());

        Comment parentComment = getParentCommentById(requestDto.parentCommentId());

        Comment comment = saveComment(user, board, parentComment, requestDto);
//        log.info(CommentResponseDto.fromComment(comment).toString());

//        return CommentResponseDto.from(comment);
    }

    private Comment getParentCommentById(Long parentCommentId) {
        if (parentCommentId == null) {
            return null;
        }
        return getCommentById(parentCommentId);
    }

    private Comment getCommentById(Long commentId) {
        Comment comment = commentDao.findById(commentId);
        if (comment == null) {
            throw new SystemException(ClientErrorCode.COMMENT_NOT_FOUND);
        }
        return comment;
    }

    private Comment saveComment(User user, Board board, Comment parentComment, CommentSaveRequestDto requestDto) {
        int commentDepth = calculateCommentDepth(parentComment);
        validateCommentDepth(commentDepth);

        Comment comment = requestDto.toEntity(user, board, parentComment, commentDepth);
        commentDao.save(comment);

        return comment;
    }

    private int calculateCommentDepth(Comment parentComment) {
        if (parentComment == null) {
            return 0;
        }
        return parentComment.getDepth() + 1;
    }

    private void validateCommentDepth(int depth) {
        if (depth > 1) {
            throw new SystemException(ClientErrorCode.COMMENT_DEPTH_EXCEEDED_ERR);
        }
    }

    private User getUserById(Long userId) {

        User user = userDao.findUserById(userId);
        if (user == null) {
            log.error("User not found with name: {}", userId);
            throw new SystemException(ClientErrorCode.USER_NOT_FOUND);
        }
        log.info("userId으로 유저 db에서 조회 후 반환 : {}", user.getUserName());
        return user;
    }

    private Board getBoardById(Long boardId) {
        Board board = boardDao.findBoardById(boardId);
        if (board == null) {
            log.error("Board not found with ID: {}", boardId);
            throw new SystemException(ClientErrorCode.BOARD_NOT_FOUND);
        }
        log.info("Board found with ID: {}", boardId);
        return board;
    }
}
