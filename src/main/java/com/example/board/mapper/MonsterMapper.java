package com.example.board.mapper;

import com.example.board.entity.Monster;
import com.example.board.entity.MonsterList;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MonsterMapper {
    List<MonsterList> getAllMonsters();
    Monster getMonster(int seq);
    void insertMonster(Monster monster);
    void updateMonster(Monster monster);
    void deleteMonster(int seq);
}
