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
@Table(name = "course")
public class Course extends Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "upload_date", length = 500, updatable = false)
    private String description;
//
//    @Override
//    public String getId() {
//        return String.valueOf(id);  // Long을 String으로 변환
//    }

}
