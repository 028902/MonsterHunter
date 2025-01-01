package com.example.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MonsterListDto {
    private int seq;
    private String name;
    private String nickname;
    private String type;
    private String weak;
}
