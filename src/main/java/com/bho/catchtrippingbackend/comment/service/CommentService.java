package com.bho.catchtrippingbackend.comment.service;

import com.bho.catchtrippingbackend.board.dao.BoardDao;
import com.bho.catchtrippingbackend.board.entity.Board;
import com.bho.catchtrippingbackend.comment.dao.CommentDao;
import com.bho.catchtrippingbackend.comment.dto.CommentDeleteRequestDto;
import com.bho.catchtrippingbackend.comment.dto.CommentResponseDto;
import com.bho.catchtrippingbackend.comment.dto.CommentSaveRequestDto;
import com.bho.catchtrippingbackend.comment.dto.CommentUpdateRequestDto;
import com.bho.catchtrippingbackend.comment.entity.Comment;
import com.bho.catchtrippingbackend.error.SystemException;
import com.bho.catchtrippingbackend.error.code.ClientErrorCode;
import com.bho.catchtrippingbackend.user.dao.UserDao;
import com.bho.catchtrippingbackend.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentDao commentDao;
    private final UserDao userDao;
    private final BoardDao boardDao;

    @Transactional
    public void save(Long userId, CommentSaveRequestDto requestDto) {
        User user = getUserById(userId);
        Board board = getBoardById(requestDto.boardId());
        log.info(requestDto.toString());

        Comment parentComment = getParentCommentById(requestDto.parentCommentId());

        Comment comment = saveComment(user, board, parentComment, requestDto);

        //id, createdAt, updateAt를 어떻게 구해올지 고민 됨.
//        log.info(CommentResponseDto.fromComment(comment).toString());

//        return CommentResponseDto.from(comment);
    }

    @Transactional
    public CommentResponseDto update(Long userId, CommentUpdateRequestDto requestDto) {
        Comment comment = getCommentById(requestDto.commentId());

        validateCommentAuthor(userId, comment.getUser().getUserId());

        comment.update(requestDto.content());

        commentDao.update(comment);

        return CommentResponseDto.from(comment);

    }

    @Transactional
    public CommentResponseDto delete(Long userId, CommentDeleteRequestDto requestDto) {
        Comment comment = getCommentById(requestDto.commentId());

        //board를 쓴 사람이거나 comment를 쓴 사람이면 통과
        validateDeleteAuthorization(userId, comment);

        validateNotDeleted(comment);

        comment.delete();

        commentDao.delete(comment);

        return CommentResponseDto.fromDeleted(comment);
    }

    @Transactional(readOnly = true)
    public List<CommentResponseDto> findParentCommentsWithPaging(Long boardId, int page, int size) {
        int offset = (page - 1) * size;
        List<Comment> parentComments = commentDao.findParentCommentsWithPaging(boardId, size, offset);

        return parentComments.stream()
                .map(CommentResponseDto::fromComment)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CommentResponseDto> findChildCommentsWithPaging(Long parentId, int page, int size) {
        int offset = (page - 1) * size;
        List<Comment> childComments = commentDao.findChildCommentsWithPaging(parentId, size, offset);

        return childComments.stream()
                .map(CommentResponseDto::fromComment)
                .collect(Collectors.toList());
    }



    private void validateNotDeleted(Comment comment) {
        if (comment.isDeleted()) {
            throw new SystemException(ClientErrorCode.COMMENT_ALREADY_DELETED_ERR);
        }
    }

    private void validateDeleteAuthorization(Long userId, Comment comment) {
        boolean isAuthorized = userId.equals(comment.getUser().getUserId()) || userId.equals(comment.getBoard().getUser().getUserId());

        if (!isAuthorized) {
            throw new SystemException(ClientErrorCode.FORBIDDEN_USER_ACCESS);
        }
    }

    private void validateCommentAuthor(Long userId, Long commentId) {
        log.info("userId : {}, commentId : {}", userId, commentId);
        if (!userId.equals(commentId)) {
            throw new SystemException(ClientErrorCode.FORBIDDEN_USER_ACCESS);
        }
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
