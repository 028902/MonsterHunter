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
    public ResponseEntity<ResponseDto> commentList(String type, int fseq){
        List<Comment> commentList = commentService.getCommentList(type, fseq);
        ResponseDto response= new ResponseDto();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Comment list retrieved successfully");
        response.setData(commentList);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/comment/insert")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<ResponseDto> insertComment(@RequestBody Comment comment){
        commentService.insertComment(comment);
        ResponseDto response = new ResponseDto();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Insert Comment successfully");
        response.setData(comment);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/comment/update/{seq}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<ResponseDto> updateComment(@PathVariable int seq, @RequestBody Comment comment){
        commentService.updateComment(seq, comment);
        ResponseDto response = new ResponseDto();
        response.setStatus(HttpStatus.OK.value());  // 상태 코드 200
        response.setMessage("Update Comment successfully");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/comment/delete/{seq}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<ResponseDto> deleteComment(@PathVariable int seq){
        commentService.deleteComment(seq);
        ResponseDto response = new ResponseDto();
        response.setStatus(HttpStatus.OK.value());  // 상태 코드 200
        response.setMessage("Delete Comment successfully");
        return ResponseEntity.ok(response);
    }
}
