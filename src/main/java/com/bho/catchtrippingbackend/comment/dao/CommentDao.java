package com.bho.catchtrippingbackend.comment.dao;

import com.bho.catchtrippingbackend.comment.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentDao {
    int save(Comment comment);
    Comment findById(Long commentId);
}
