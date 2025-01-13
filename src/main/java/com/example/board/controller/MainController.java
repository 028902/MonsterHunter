package com.example.board.controller;

import com.example.board.dto.ResponseDto;
import com.example.board.dto.SearchAllDto;
import com.example.board.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
    @Autowired
    private MainService mainService;

    @GetMapping("/")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Void> index(){
        return ResponseEntity.ok().build();
    }

    @GetMapping("/main/search")
    public ResponseEntity<ResponseDto> search(@RequestParam String keyword) {
        ResponseDto response = new ResponseDto();
        SearchAllDto searchResult = mainService.search(keyword);
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Main search list retrieved successfully");
        response.setData(searchResult);
        return ResponseEntity.ok(response);
    }
}
