package com.example.board.repository;

import com.example.board.dto.MonsterListDto;
import com.example.board.entity.Monster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MonsterRepository extends JpaRepository<Monster, Integer> {
    @Query(value = "SELECT new com.example.board.dto.MonsterListDto(m.seq, m.name, m.nickname, m.type, m.weak) FROM Monster m ORDER BY m.seq DESC",
    countQuery = "SELECT COUNT(m) FROM Monster m")
    Page<MonsterListDto> findMonsterList(Pageable pageable);
}
