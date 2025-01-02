package com.example.board.service;

import com.example.board.dto.MonsterListDto;
import com.example.board.entity.Monster;
import com.example.board.repository.MonsterRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MonsterService {
    private final MonsterRepository monsterRepository;

    public Page<MonsterListDto> findMonsterList(Pageable pageable) {
        return monsterRepository.findMonsterList(pageable);
    }

    public Monster getMonsterDetail(int seq) {
        return monsterRepository.findById(seq)
                .orElseThrow(() -> new EntityNotFoundException("Monster with ID " + seq + " not found"));
    }

    public Monster insertMonster(Monster monster) {
        return monsterRepository.save(monster);
    }

    public Monster updateMonster(int seq, Monster updatedMonster) {
        Monster monster = monsterRepository.findById(seq).orElseThrow(() -> new EntityNotFoundException("Monster " + seq + "not found"));
        if (updatedMonster.getName() != null) monster.setName(updatedMonster.getName());
        if (updatedMonster.getNickname() != null) monster.setNickname(updatedMonster.getNickname());
        if (updatedMonster.getIcon() != null) monster.setIcon(updatedMonster.getIcon());
        if (updatedMonster.getImg() != null) monster.setImg(updatedMonster.getImg());
        if (updatedMonster.getType() != null) monster.setType(updatedMonster.getType());
        if (updatedMonster.getDescription() != null) monster.setDescription(updatedMonster.getDescription());
        if (updatedMonster.getWeak() != null) monster.setWeak(updatedMonster.getWeak());
        monster.setPoi(updatedMonster.getPoi());
        monster.setSle(updatedMonster.getSle());
        monster.setPar(updatedMonster.getPar());
        monster.setBla(updatedMonster.getBla());
        monster.setStu(updatedMonster.getStu());
        return monsterRepository.save(monster);
    }

    public void deleteMonster(int seq) {
        monsterRepository.findById(seq).orElseThrow(() -> new EntityNotFoundException("Monster " + seq + "not found"));
        monsterRepository.deleteById(seq);
    }
}
