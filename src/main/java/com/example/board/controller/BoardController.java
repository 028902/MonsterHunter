package com.example.board.controller;

import com.example.board.dto.BoardListDto;
import com.example.board.entity.Board;
import com.example.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        Board board = boardService.getBoardDetail(seq);
        return ResponseEntity.ok(board);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/board/insert")
    public ResponseEntity<String> insertParty(@RequestBody Board board) {
        boardService.insertBoard(board);
        return ResponseEntity.ok("Insert board successfully");
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/board/update/{seq}")
    public ResponseEntity<?> updateBoard(@PathVariable int seq, @RequestBody Board board) {
        boardService.updateBoard(seq, board);
        return ResponseEntity.ok("Update board successfully");
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping("/board/delete/{seq}")
    public ResponseEntity<?> deleteBoard(@PathVariable int seq) {
        boardService.deleteBoard(seq);
        return ResponseEntity.ok("Delete board successfully");
    }
}
