package com.example.board.controller;

import com.example.board.dto.ResponseDto;
import com.example.board.entity.Comment;
import com.example.board.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/comment/list")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<List<Comment>> commentList(String type, int fseq){
        List<Comment> commentList = commentService.getCommentList(type, fseq);
        return ResponseEntity.ok(commentList);
    }

    @PostMapping("/comment/insert")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Comment> insertComment(@RequestBody Comment comment){
        commentService.insertComment(comment);
        return ResponseEntity.ok(comment);
    }

    @PutMapping("/comment/update/{seq}")
    @CrossOrigin(origins = "http://localhost:3000")
    public void updateComment(@PathVariable int seq, @RequestBody Comment comment){
        commentService.updateComment(seq, comment);
    }

    @DeleteMapping("/comment/delete/{seq}")
    @CrossOrigin(origins = "http://localhost:3000")
    public void deleteComment(@PathVariable int seq){
        commentService.deleteComment(seq);
    }
}
