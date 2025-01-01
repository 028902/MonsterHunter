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
        try{
            Monster monster = monsterService.getMonsterDetail(seq);
            return ResponseEntity.ok(monster);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/admin/monster/insert")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<String> insertMonster(@RequestBody Monster monster) {
        try {
            monsterService.insertMonster(monster);
            return ResponseEntity.ok("Monster added successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Insert Monster failed" + e.getMessage());
        }
    }

    @PutMapping("/admin/monster/update/{seq}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> updateMonster(@PathVariable int seq, @RequestBody Monster monster) {
        try {
            monsterService.updateMonster(seq, monster);
            return ResponseEntity.ok("Monster updated successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()); // 404 Not Found
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Update Monster failed");
        }
    }

    @DeleteMapping("/admin/monster/delete/{seq}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> deleteMonster(@PathVariable int seq) {
        try {
            monsterService.deleteMonster(seq);
            return ResponseEntity.ok("Monster deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()); // 404 Not Found
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Delete Monster failed");
        }
    }
}
