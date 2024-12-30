package com.example.board.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Monster {
    @JsonProperty("seq")
    private int seq;

    @JsonProperty("name")
    private String name;

    @JsonProperty("img")
    private String img;

    @JsonProperty("icon")
    private String icon;

    @JsonProperty("type")
    private String type;

    @JsonProperty("body")
    private String body;
}
