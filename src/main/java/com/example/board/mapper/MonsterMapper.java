package com.example.board.mapper;

import com.example.board.entity.Monster;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MonsterMapper {
    List<Monster> getAllMonsters();
    void insertMonster(Monster monster);
    void updateMonster(Monster monster);
    void deleteMonster(int seq);
}
