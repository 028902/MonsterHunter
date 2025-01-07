package com.example.board.dto;

import lombok.Data;

@Data
public class ResponseDto {
    private int status;
    private String message;
    private Object data;
}