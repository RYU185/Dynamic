package com.dw.dynamic.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "cart")
public class Cart {
    @Id
    @Column(name = "cart_id")
    private String cartId;

    @Column(name = "quantity")
    private int quantity;

    @OneToOne
    @JoinColumn(name = "user_name")
    private User user; // 유저(단방향 1:1)

    @OneToMany(mappedBy = "product")
    private List<Product> product;
}
