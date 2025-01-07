package com.example.board.controller;

import com.example.board.dto.BoardListDto;
import com.example.board.dto.ResponseDto;
import com.example.board.entity.Board;
import com.example.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class BoardController {
    @Autowired
    private BoardService boardService;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/board/list")
    public ResponseEntity<ResponseDto> boardList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<BoardListDto> boardList = boardService.findBoardList(
                PageRequest.of(page, size, Sort.by("seq").descending())
        );
        ResponseDto response = new ResponseDto();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Board list retrieved successfully");
        response.setData(boardList);
        return ResponseEntity.ok(response);
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/board/list/search")
    public ResponseEntity<ResponseDto> searchBoardList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String nickname,
            @RequestParam(required = false) String title
    ){
        Page<BoardListDto> boardList = boardService.searchBoardList(
                nickname, title, PageRequest.of(page, size, Sort.by("seq").descending())
        );
        ResponseDto response = new ResponseDto();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Search results retrieved successfully");
        response.setData(boardList);
        return ResponseEntity.ok(response);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/board/detail/{seq}")
    public ResponseEntity<ResponseDto> boardDetail(@PathVariable int seq) {
        Board board = boardService.getBoardDetail(seq);
        ResponseDto response = new ResponseDto();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Board details fetched successfully");
        response.setData(board);
        return ResponseEntity.ok(response);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/board/insert")
    public ResponseEntity<ResponseDto> insertBoard(@RequestBody Board board) {
        boardService.insertBoard(board);
        ResponseDto response = new ResponseDto();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Insert board successfully");
        response.setData(board);
        return ResponseEntity.ok(response);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/board/update/{seq}")
    public ResponseEntity<ResponseDto> updateBoard(@PathVariable int seq, @RequestBody Board board) {
        boardService.updateBoard(seq, board);
        ResponseDto response = new ResponseDto();
        response.setStatus(HttpStatus.OK.value());  // 상태 코드 200
        response.setMessage("Update board successfully");
        return ResponseEntity.ok(response);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping("/board/delete/{seq}")
    public ResponseEntity<ResponseDto> deleteBoard(@PathVariable int seq) {
        boardService.deleteBoard(seq);
        ResponseDto response = new ResponseDto();
        response.setStatus(HttpStatus.OK.value());  // 상태 코드 200
        response.setMessage("Delete board successfully");
        return ResponseEntity.ok(response);
    }
}
