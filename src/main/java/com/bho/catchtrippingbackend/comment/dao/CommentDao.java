package com.bho.catchtrippingbackend.comment.dao;

import com.bho.catchtrippingbackend.comment.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentDao {
    int save(Comment comment);
    Comment findById(Long commentId);
    int update(Comment comment);
    int delete(Comment comment); // delete = true 하는 논리적 삭제
}
