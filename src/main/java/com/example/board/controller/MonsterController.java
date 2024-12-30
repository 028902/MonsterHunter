package com.example.board.controller;

import com.example.board.entity.Monster;
import com.example.board.service.MonsterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MonsterController {
    @Autowired
    private MonsterService monsterService;


    @GetMapping("/")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<Void> index(){
        return ResponseEntity.ok().build();
    }

    @GetMapping("/monster/list")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public List<Monster> monsterList(){
        return monsterService.getAllMonsters();
    }

    @PostMapping("/admin/monster/insert")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<String> insertMonster(@RequestBody Monster monster) {
        // 유효성 검사
        if (monster.getName() == null || monster.getName().isEmpty()) {
            return ResponseEntity.badRequest().body("Name cannot be empty");
        }
        if (monster.getImg() == null || monster.getImg().isEmpty()) {
            return ResponseEntity.badRequest().body("Image URL cannot be empty");
        }
        if (monster.getBody() == null || monster.getBody().isEmpty()) {
            return ResponseEntity.badRequest().body("Body cannot be empty");
        }
        try {
            monsterService.insertMonster(monster);
            return ResponseEntity.status(HttpStatus.CREATED).body("Monster added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to insert monster: " + e.getMessage());
        }
    }

    @PutMapping("/admin/monster/update")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<?> updateMonster(@RequestBody Monster monster) {
        try {
            monsterService.updateMonster(monster);
            return ResponseEntity.ok("Monster updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update monster: " + e.getMessage());
        }
    }

    @DeleteMapping("/admin/monster/delete")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<?> deleteMonster(@RequestParam int seq) {
        try {
            monsterService.deleteMonster(seq);
            return ResponseEntity.ok("Monster deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete monster: " + e.getMessage());
        }
    }
}
