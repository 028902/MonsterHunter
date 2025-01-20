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

    @GetMapping("/board/list")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Page<BoardListDto>> boardList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<BoardListDto> boardList = boardService.findBoardList(
                PageRequest.of(page, size, Sort.by("seq").descending())
        );
        return ResponseEntity.ok(boardList);
    }
    @GetMapping("/board/list/search")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Page<BoardListDto>> searchBoardList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String nickname,
            @RequestParam(required = false) String title
    ){
        Page<BoardListDto> boardList = boardService.searchBoardList(
                nickname, title, PageRequest.of(page, size, Sort.by("seq").descending())
        );
        return ResponseEntity.ok(boardList);
    }

    @GetMapping("/board/detail/{seq}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Board> boardDetail(@PathVariable int seq) {
        Board board = boardService.getBoardDetail(seq);
        return ResponseEntity.ok(board);
    }

    @PostMapping("/board/insert")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Board> insertBoard(@RequestBody Board board) {
        boardService.insertBoard(board);
        return ResponseEntity.ok(board);
    }

    @PutMapping("/board/update/{seq}")
    @CrossOrigin(origins = "http://localhost:3000")
    public void updateBoard(@PathVariable int seq, @RequestBody Board board) {
        boardService.updateBoard(seq, board);
    }

    @DeleteMapping("/board/delete/{seq}")
    @CrossOrigin(origins = "http://localhost:3000")
    public void deleteBoard(@PathVariable int seq) {
        boardService.deleteBoard(seq);
    }
}
