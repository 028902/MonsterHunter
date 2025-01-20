package com.example.board.controller;

import com.example.board.dto.MonsterListDto;
import com.example.board.entity.Monster;
import com.example.board.service.MonsterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MonsterController {
    @Autowired
    private MonsterService monsterService;

    @GetMapping("/monster/list")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Page<MonsterListDto>> monsterList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<MonsterListDto> monsterList = monsterService.findMonsterList(
                PageRequest.of(page, size, Sort.by("seq").descending())
        );
        return ResponseEntity.ok(monsterList);
    }

    @GetMapping("/monster/list/search")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Page<MonsterListDto>> monsterListSearch(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String nickname,
            @RequestParam(required = false) String type
    ){
        Page<MonsterListDto> monsterList = monsterService.searchMonsterList(
                name, nickname, type, PageRequest.of(page, size, Sort.by("seq").descending())
        );
        return ResponseEntity.ok(monsterList);
    }

    @GetMapping("/monster/detail/{seq}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Monster> monsterDetail(@PathVariable int seq){
        Monster monster = monsterService.getMonsterDetail(seq);
        return ResponseEntity.ok(monster);
    }

    @PostMapping("/admin/monster/insert")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Monster> insertMonster(@RequestBody Monster monster) {
        monsterService.insertMonster(monster);
        return ResponseEntity.ok(monster);
    }

    @PutMapping("/admin/monster/update/{seq}")
    @CrossOrigin(origins = "http://localhost:3000")
    public void updateMonster(@PathVariable int seq, @RequestBody Monster monster) {
        monsterService.updateMonster(seq, monster);
    }

    @DeleteMapping("/admin/monster/delete/{seq}")
    @CrossOrigin(origins = "http://localhost:3000")
    public void deleteMonster(@PathVariable int seq) {
        monsterService.deleteMonster(seq);
    }
}
