package com.example.board.repository;

import com.example.board.dto.BoardListDto;
import com.example.board.dto.MonsterListDto;
import com.example.board.entity.Monster;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MainRepository extends JpaRepository<Monster, Integer> {
    @Query(value = "SELECT new com.example.board.dto.BoardListDto(b.seq, b.nickname, b.title, b.count, b.regDate) " +
            "FROM Board b " +
            "WHERE (:keyword IS NULL " +
            "OR b.nickname LIKE %:keyword% " +
            "OR b.title LIKE %:keyword%) " +
            "ORDER BY b.seq DESC")
    Page<BoardListDto> searchAllBoardList(@Param("keyword") String keyword, Pageable pageable);

    @Query(value = "SELECT new com.example.board.dto.MonsterListDto(m.seq, m.name, m.nickname, m.type, m.weak) " +
            "FROM Monster m " +
            "WHERE (:keyword IS NULL OR m.name LIKE %:keyword% " +
            "OR m.nickname LIKE %:keyword% " +
            "OR m.type LIKE %:keyword% " +
            "OR m.weak LIKE %:keyword%) " +
            "ORDER BY m.seq DESC")
    Page<MonsterListDto> searchAllMonsterList(@Param("keyword") String keyword, Pageable pageable);

}
