package com.dw.dynamic.model;

import com.dw.dynamic.enums.Rating;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "text", length = 1000, nullable = false)
    private String text;

    @Column(name = "rating",nullable = false)
    @Enumerated(EnumType.STRING)
    private Rating rating; // ENUM 수정 필요
    
    @Column(name = "add_date", updatable = false)
    private LocalDate addDate;  // 작성일

    @Column(name = "modified_date")
    private LocalDate modifiedDate; // 수정일

    @Column(name="is_active")
    private Boolean isActive = true;

    @ManyToOne
    @JoinColumn(name = "user_name")
    private User user; // 유저 - 리뷰 (단방향)

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;



}
