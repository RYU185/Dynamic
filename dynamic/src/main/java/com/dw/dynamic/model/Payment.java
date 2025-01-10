package com.dw.dynamic.model;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "payment")
public class Payment {
    @Id
    @Column(name = "payment_name")
    private String paymentName;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart_fk; // 장바구니 - 제품 (양방향)

}
