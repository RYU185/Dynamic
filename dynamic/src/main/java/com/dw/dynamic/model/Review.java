package com.dw.dynamic.model;

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

    @ManyToOne
    @JoinColumn(name = "purchase_details")
    private PurchaseDetails purchaseDetails;

    @Column(name = "text", length = 1000, nullable = false)
    private String text;

    @Column(name = "rating",nullable = false)
    @Enumerated(EnumType.STRING)
    private String rating; // ENUM 수정 필요
    
    @Column(name = "date", updatable = false)
    private LocalDate addDate;

    @Column(name = "modified_date")
    private LocalDate modifiedDate;

    @ManyToOne
    @JoinColumn(name = "userName")
    private User user; // 유저(단방향)



}
