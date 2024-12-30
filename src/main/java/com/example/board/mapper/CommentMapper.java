package com.example.board.mapper;

import com.example.board.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {
    List<Comment> getCommentList(String type);
    void insertComment(Comment comment);
    void updateComment(Comment comment);
    void deleteComment(String type, int seq);
}
