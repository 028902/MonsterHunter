package com.example.board.entity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "BOARD")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // PK, AI
    private int seq;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(nullable = false, columnDefinition = "MEDIUMTEXT")
    private String body;

    @Column
    private int count;

    @Column(name = "reg_date", updatable = false)
    private LocalDateTime regDate;

    @PrePersist
    protected void onCreate() {
        this.regDate = LocalDateTime.now();
        this.count = 0;
    }
}
