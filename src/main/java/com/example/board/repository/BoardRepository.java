package com.example.board.repository;

import com.example.board.dto.BoardListDto;
import com.example.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BoardRepository extends JpaRepository<Board, Integer> {
    @Query(value = "SELECT new com.example.board.dto.BoardListDto(b.seq, b.nickname, b.title, b.count, b.regDate) FROM Board b ORDER BY b.seq DESC",
    countQuery = "SELECT COUNT(b) FROM Board b")
    Page<BoardListDto> findBoardList(Pageable pageable);
}
