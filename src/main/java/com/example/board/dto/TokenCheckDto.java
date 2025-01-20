package com.example.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenCheckDto {
    private String accessToken;
    private String refreshToken;
}
