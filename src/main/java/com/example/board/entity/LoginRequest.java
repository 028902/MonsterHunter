package com.example.board.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Data
public class LoginRequest {

    @JsonProperty("id")
    private String id;

    @JsonProperty("password")
    private String password;
}
