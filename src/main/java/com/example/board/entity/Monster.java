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

    @JsonProperty("nickname")
    private String nickname;

    @JsonProperty("icon")
    private String icon;

    @JsonProperty("img")
    private String img;

    @JsonProperty("type")
    private String type;

    @JsonProperty("description")
    private String description;

    @JsonProperty("weak")
    private String weak;

    @JsonProperty("poi")
    private String poi;

    @JsonProperty("sle")
    private String sle;

    @JsonProperty("par")
    private String par;

    @JsonProperty("bla")
    private String bla;

    @JsonProperty("stu")
    private String stu;
}
