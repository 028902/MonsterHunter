package com.example.board.controller;

import com.example.board.dto.MonsterListDto;
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

    @GetMapping("/")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Void> index(){
        return ResponseEntity.ok().build();
    }

    @GetMapping("/monster/list")
    @CrossOrigin(origins = "http://localhost:3000")
    public Page<MonsterListDto> monsterList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return monsterService.findMonsterList(
                PageRequest.of(page, size, Sort.by("seq").descending())
        );
    }

    @GetMapping("/monster/detail/{seq}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Monster> monsterDetail(@PathVariable int seq){
        Monster monster = monsterService.getMonsterDetail(seq);
        return ResponseEntity.ok(monster);
    }

    @PostMapping("/admin/monster/insert")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<String> insertMonster(@RequestBody Monster monster) {
        monsterService.insertMonster(monster);
        return ResponseEntity.ok("Monster added successfully");
    }

    @PutMapping("/admin/monster/update/{seq}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> updateMonster(@PathVariable int seq, @RequestBody Monster monster) {
        monsterService.updateMonster(seq, monster);
        return ResponseEntity.ok("Monster updated successfully");
    }

    @DeleteMapping("/admin/monster/delete/{seq}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> deleteMonster(@PathVariable int seq) {
        monsterService.deleteMonster(seq);
        return ResponseEntity.ok("Monster deleted successfully");
    }
}
