package com.dw.dynamic.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.mapping.Join;

import java.time.LocalDate;

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

    @Column(name = "notice_title", length = 3000, nullable = false)
    private String noticeTitle; // 제목

    @Column(name ="text",length = 3000, nullable = false)
    private String text; // 본문

    @Column(name = "add_date", nullable = false)
    private LocalDate addDate; // 작성일자

    @Column(name = "formation_data")
    private String formationData; // 외부 파일( 서식 자료)

    @ManyToOne
    @JoinColumn(name = "userName")
    private User user; // 유저(단방향)
}
