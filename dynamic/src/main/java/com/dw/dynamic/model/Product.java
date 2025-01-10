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
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name = "product")
public class Product {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "price")
    private double price;

    @ManyToOne
    @JoinColumn(name = "category")
    private Category category; // 제품 - 카테고리 (단방향)

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart_fk; // 장바구니(양방향)

}
