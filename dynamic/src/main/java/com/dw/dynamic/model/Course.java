package com.dw.dynamic.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "course")
public class Course extends Product {
//  추가 시 , product를 하나 만들고 추가해야함!!

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "add_date", updatable = false)
    private LocalDate addDate;// 강의 업로드 날짜

    @Column(name = "description", length = 500, updatable = false)
    private String description; // 상세 설명


}
