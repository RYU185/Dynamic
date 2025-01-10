package com.dw.dynamic.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.mapping.Join;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "notice")
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noticeId;

    @ManyToOne
    @JoinColumn(name = "userName")
    private User user; // 유저(단방향)
}
