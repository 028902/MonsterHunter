package com.example.board.service;

import com.example.board.entity.Comment;
import com.example.board.repository.CommentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public List<Comment> getCommentList(String type, int fseq){
        return commentRepository.findAllByTypeAndFseq(type, fseq);
    }
    public Comment insertComment(Comment comment){
        return commentRepository.save(comment);
    }

    public Comment updateComment(int seq, Comment updatedComment){
        Comment comment = commentRepository.findById(seq).orElseThrow(() -> new EntityNotFoundException("Comment" + seq + " not found"));
        if(updatedComment.getNickname() != null) comment.setNickname(updatedComment.getNickname());
        if(updatedComment.getBody() != null) comment.setBody(updatedComment.getBody());
        return commentRepository.save(comment);
    }
    public void deleteComment(int seq){
        commentRepository.findById(seq).orElseThrow(() -> new EntityNotFoundException("Comment" + seq + " not found"));
        commentRepository.deleteById(seq);
    }
}
