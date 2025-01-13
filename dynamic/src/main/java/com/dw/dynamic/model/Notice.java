package com.dw.dynamic.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.mapping.Join;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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


}
