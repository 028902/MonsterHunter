package com.example.board.controller;

import com.example.board.entity.Party;
import com.example.board.service.PartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PartyController {
    @Autowired
    private PartyService partyService;

    @GetMapping("/party/list")
    public List<Party> partyList() {
        return partyService.getPartyList();
    }

    @PostMapping("/party/insert")
    public ResponseEntity<String> insertParty(@RequestBody Party party) {
        try {
            partyService.insertParty(party);
            return ResponseEntity.ok("Insert party successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Insert party failed");
        }
    }

    @PutMapping("/party/update")
    public ResponseEntity<?> updateParty(@RequestBody Party party) {
        try{
            partyService.updateParty(party);
            return ResponseEntity.ok("Update party successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Update party failed");
        }
    }

    @DeleteMapping("/party/delete")
    public ResponseEntity<?> deleteParty(@RequestParam int seq) {
        try {
            partyService.deleteParty(seq);
            return ResponseEntity.ok("Delete party successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Delete party failed");
        }
    }
}
