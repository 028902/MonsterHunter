package com.example.board.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "MONSTER")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Monster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // PK, AI
    private int seq;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String icon;

    @Column(nullable = false)
    private String img;

    @Column(nullable = false)
    private String type;

    @Lob
    @Column(nullable = false, columnDefinition = "MEDIUMTEXT")
    private String description;

    @Column(nullable = false)
    private String weak;

    @Column(nullable = false)
    private int poi;

    @Column(nullable = false)
    private int sle;

    @Column(nullable = false)
    private int par;

    @Column(nullable = false)
    private int bla;

    @Column(nullable = false)
    private int stu;
}
