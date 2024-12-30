package com.example.board.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @JsonProperty("seq")
    private int seq;

    @JsonProperty("type")
    private String type;

    @JsonProperty("id")
    private String id;

    @JsonProperty("body")
    private String body;

    @JsonProperty("reg_date")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    private OffsetDateTime reg_date;
}
