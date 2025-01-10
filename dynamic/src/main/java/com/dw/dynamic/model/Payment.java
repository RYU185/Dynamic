package com.dw.dynamic.model;

import com.dw.dynamic.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "payment")
public class Payment {
    @Id
    @Column(name = "payment_id")
    private Long paymentId;

    @Column(name = "total_amount", nullable = false)
    private Long totalAmount;// 총금액

    @Column(name = "payment_method",nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod; // ENUM 수정 필요

    @Column(name = "card_number", length = 15)
    private String CardNumber;

    @Column(name = "card_expiration_date")
    private LocalDate cardExpirationDate;

    @Column(name = "cvc", length = 3)
    private int cvc;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart_fk; // 장바구니 - 제품 (양방향)

}
