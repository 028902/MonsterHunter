package com.example.board.service;

import com.example.board.entity.Comment;
import com.example.board.mapper.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    @Autowired
    CommentMapper commentMapper;

    public List<Comment> getCommentList(String type){
        return commentMapper.getCommentList(type);
    }
    public void insertComment(Comment comment){
        commentMapper.insertComment(comment);
    }
    public void updateComment(Comment comment){
        commentMapper.updateComment(comment);
    }
    public void deleteComment(String type, int seq){
        commentMapper.deleteComment(type, seq);
    }
}
