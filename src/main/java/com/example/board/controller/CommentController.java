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
    public List<Comment> commentList(String type){
        return commentService.getCommentList(type);
    }

    @PostMapping("/comment/insert")
    public ResponseEntity<?> insertComment(@RequestBody Comment comment){
        try{
            commentService.insertComment(comment);
            return ResponseEntity.ok("Insert Comment successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Insert Comment failed");
        }
    }

    @PutMapping("/comment/update")
    public ResponseEntity<?> updateComment(@RequestBody Comment comment){
        try{
            commentService.updateComment(comment);
            return ResponseEntity.ok("Update Comment successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Update Comment failed");
        }
    }

    @DeleteMapping("/comment/delete")
    public ResponseEntity<?> deleteComment(@RequestParam String type, @RequestParam int seq){
        try{
            commentService.deleteComment(type, seq);
            return ResponseEntity.ok("Delete Comment successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Delete Comment failed");
        }
    }
}
