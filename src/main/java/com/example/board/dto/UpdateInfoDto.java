package com.example.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateInfoDto {
    private String password;
    private String email;
    private String nickname;
    private String weapon;
}
