package com.example.board.controller;

import com.example.board.entity.Board;
import com.example.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BoardController {
    @Autowired
    private BoardService boardService;

    @GetMapping("/board/list")
    public List<Board> boardList() {
        return boardService.getBoardList();
    }

    @PostMapping("/board/insert")
    public ResponseEntity<String> insertParty(@RequestBody Board board) {
        try {
            boardService.insertBoard(board);
            return ResponseEntity.ok("Insert board successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Insert board failed");
        }
    }

    @PutMapping("/board/update")
    public ResponseEntity<?> updateBoard(@RequestBody Board board) {
        try{
            boardService.updateBoard(board);
            return ResponseEntity.ok("Update board successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Update board failed");
        }
    }

    @DeleteMapping("/board/delete")
    public ResponseEntity<?> deleteBoard(@RequestParam int seq) {
        try {
            boardService.deleteBoard(seq);
            return ResponseEntity.ok("Delete board successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Delete board failed");
        }
    }
}
