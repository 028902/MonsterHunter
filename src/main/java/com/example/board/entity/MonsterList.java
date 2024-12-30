package com.example.board.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MonsterList {
    @JsonProperty("seq")
    private int seq;

    @JsonProperty("name")
    private String name;

    @JsonProperty("nickname")
    private String nickname;

    @JsonProperty("type")
    private String type;

    @JsonProperty("weak")
    private String weak;
}
