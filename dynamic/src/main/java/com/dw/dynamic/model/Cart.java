package com.dw.dynamic.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
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
    private Long quantity;

    @OneToOne
    @JoinColumn(name = "user_name")
    private User user; // 장바구니 - 유저(단방향 1:1)

    @OneToMany(mappedBy = "cart_fk")
    private List<Product> productList = new ArrayList<>();  // 장바구니 - 제품 (양방향)

    @OneToMany(mappedBy = "cart_fk")
    private List<Payment> paymentList = new ArrayList<>();  // 장바구니 - 결제 (양방향)
}
