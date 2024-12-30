package com.example.board.service;

import com.example.board.entity.Monster;
import com.example.board.mapper.MonsterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MonsterService {
    @Autowired
    MonsterMapper monsterMapper;

    public List<Monster> getAllMonsters() {
        return monsterMapper.getAllMonsters();
    }
    public void insertMonster(Monster monster) {
        monsterMapper.insertMonster(monster);
    }
    public void updateMonster(Monster monster) {
        monsterMapper.updateMonster(monster);
    }
    public void deleteMonster(int seq) {
        monsterMapper.deleteMonster(seq);
    }
}
