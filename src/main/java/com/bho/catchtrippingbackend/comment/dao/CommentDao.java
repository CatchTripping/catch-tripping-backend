package com.bho.catchtrippingbackend.comment.dao;

import com.bho.catchtrippingbackend.comment.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentDao {
    int save(Comment comment);
    Comment findById(Long commentId);
    int update(Comment comment);
    int delete(Comment comment); // delete = true 하는 논리적 삭제
    List<Comment> findParentCommentsWithPaging(@Param("boardId") Long boardId, @Param("size") int size, @Param("offset") int offset);
    List<Comment> findChildCommentsWithPaging(@Param("parentId") Long parentId, @Param("size") int size, @Param("offset") int offset);
}
