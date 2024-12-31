package com.example.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class BoardListDto {
    private int seq;
    private String nickname;
    private String title;
    private int count;
    private LocalDateTime regDate;
}
