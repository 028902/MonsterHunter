package com.example.board.dto;

import lombok.Data;

import java.util.List;

@Data
public class SearchAllDto {
    private List<MonsterListDto> monsters;
    private List<BoardListDto> boards;
    private long totalMonsterCount;
    private long totalBoardCount;
}
