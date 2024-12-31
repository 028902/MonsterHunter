package com.example.board.controller;

import com.example.board.dto.BoardListDto;
import com.example.board.entity.Board;
import com.example.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BoardController {
    @Autowired
    private BoardService boardService;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/board/list")
    public Page<BoardListDto> boardList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return boardService.findBoardList(
                PageRequest.of(page, size, Sort.by("seq").descending())
        );
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/board/detail/{seq}")
    public ResponseEntity<Board> boardDetail(@PathVariable int seq) {
        try {
            Board board = boardService.getBoardDetail(seq);
            return ResponseEntity.ok(board);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/board/insert")
    public ResponseEntity<String> insertParty(@RequestBody Board board) {
        try {
            boardService.insertBoard(board);
            return ResponseEntity.ok("Insert board successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Insert board failed");
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/board/update/{seq}")
    public ResponseEntity<?> updateBoard(@PathVariable int seq, @RequestBody Board board) {
        try{
            boardService.updateBoard(seq, board);
            return ResponseEntity.ok("Update board successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()); // 404 Not Found
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Update board failed");
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping("/board/delete/{seq}")
    public ResponseEntity<?> deleteBoard(@PathVariable int seq) {
        try {
            boardService.deleteBoard(seq);
            return ResponseEntity.ok("Delete board successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()); // 404 Not Found
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Delete board failed");
        }
    }
}
