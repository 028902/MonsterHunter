package com.example.board.controller;

import com.example.board.dto.MonsterListDto;
import com.example.board.dto.ResponseDto;
import com.example.board.entity.Monster;
import com.example.board.service.MonsterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MonsterController {
    @Autowired
    private MonsterService monsterService;

    @GetMapping("/monster/list")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<ResponseDto> monsterList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<MonsterListDto> monsterList = monsterService.findMonsterList(
                PageRequest.of(page, size, Sort.by("seq").descending())
        );
        ResponseDto response = new ResponseDto();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Monster list retrieved successfully");
        response.setData(monsterList);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/monster/list/search")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<ResponseDto> monsterListSearch(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String nickname,
            @RequestParam(required = false) String type
    ){
        Page<MonsterListDto> monsterList = monsterService.searchMonsterList(
                name, nickname, type, PageRequest.of(page, size, Sort.by("seq").descending())
        );
        ResponseDto response = new ResponseDto();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Search results retrieved successfully");
        response.setData(monsterList);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/monster/detail/{seq}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<ResponseDto> monsterDetail(@PathVariable int seq){
        Monster monster = monsterService.getMonsterDetail(seq);
        ResponseDto response = new ResponseDto();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Monster details fetched successfully");
        response.setData(monster);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/admin/monster/insert")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<ResponseDto> insertMonster(@RequestBody Monster monster) {
        monsterService.insertMonster(monster);
        ResponseDto response = new ResponseDto();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Insert monster successfully");
        response.setData(monster);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/admin/monster/update/{seq}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<ResponseDto> updateMonster(@PathVariable int seq, @RequestBody Monster monster) {
        monsterService.updateMonster(seq, monster);
        ResponseDto response = new ResponseDto();
        response.setStatus(HttpStatus.OK.value());  // 상태 코드 200
        response.setMessage("Update monster successfully");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/admin/monster/delete/{seq}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<ResponseDto> deleteMonster(@PathVariable int seq) {
        monsterService.deleteMonster(seq);
        ResponseDto response = new ResponseDto();
        response.setStatus(HttpStatus.OK.value());  // 상태 코드 200
        response.setMessage("Delete monster successfully");
        return ResponseEntity.ok(response);
    }
}
