package com.example.board.repository;

import com.example.board.dto.MonsterListDto;
import com.example.board.entity.Monster;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MonsterRepository extends JpaRepository<Monster, Integer> {
    @Query(value = "SELECT new com.example.board.dto.MonsterListDto(m.seq, m.name, m.nickname, m.type, m.weak) FROM Monster m ORDER BY m.seq DESC",
    countQuery = "SELECT COUNT(m) FROM Monster m")
    Page<MonsterListDto> findMonsterList(Pageable pageable);

    @Query(value = "SELECT new com.example.board.dto.MonsterListDto(m.seq, m.name, m.nickname, m.type, m.weak) " +
            "FROM Monster m " +
            "WHERE (:name IS NULL OR m.name LIKE %:name%) " +
            "AND (:nickname IS NULL OR m.nickname LIKE %:nickname%) " +
            "AND (:type IS NULL OR m.type LIKE %:type%) " +
            "ORDER BY m.seq DESC")
    Page<MonsterListDto> searchMonsterList(@Param("name") String name,
                                        @Param("nickname") String nickname,
                                        @Param("type") String type,
                                        Pageable pageable);
}
