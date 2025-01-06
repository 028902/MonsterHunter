package com.example.board.controller;

import com.example.board.entity.Comment;
import com.example.board.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/comment/list")
    public List<Comment> commentList(String type, int fseq){
        return commentService.getCommentList(type, fseq);
    }

    @PostMapping("/comment/insert")
    public ResponseEntity<?> insertComment(@RequestBody Comment comment){
        commentService.insertComment(comment);
        return ResponseEntity.ok("Insert Comment successfully");
    }

    @PutMapping("/comment/update/{seq}")
    public ResponseEntity<?> updateComment(@PathVariable int seq, @RequestBody Comment comment){
        commentService.updateComment(seq, comment);
        return ResponseEntity.ok("Update Comment successfully");
    }

    @DeleteMapping("/comment/delete/{seq}")
    public ResponseEntity<?> deleteComment(@PathVariable int seq){
        commentService.deleteComment(seq);
        return ResponseEntity.ok("Delete Comment successfully");
    }
}
