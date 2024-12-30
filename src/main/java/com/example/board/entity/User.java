package com.example.board.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @JsonProperty("seq")
    private int seq;

    @JsonProperty("id")
    private String id;

    @JsonProperty("nickname")
    private String nickname;

    @JsonProperty("password")
    private String password;

    @JsonProperty("email")
    private String email;

    @JsonProperty("weapon")
    private String weapon;

    @JsonProperty("reg_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate reg_date;
}
