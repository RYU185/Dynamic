package com.dw.dynamic.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "board")
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title",nullable = false)
    private String title;

    @Column(name = "answer", nullable = false, length = 3000)
    private boolean answer; // 관리자 답변 여부

    @Column(name = "add_date", updatable = false)
    private LocalDateTime addDate; // 작성일

    @Column(name="is_active")
    private Boolean isActive = true;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // 유저(단방향)

    @OneToMany(mappedBy = "board_fk")
    private List<Comment> commentList = new ArrayList<>();
}
