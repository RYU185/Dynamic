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
@Table(name = "purchase_details")
public class PurchaseDetails {
    @Id
    @Column(name = "id")
    private String id;

    @OneToMany(mappedBy = "purchase_details")
    private List<Review> reviewList = new ArrayList<>(); // 구매내역 - 리뷰 (양방향)

    @ManyToOne
    @JoinColumn(name = "payment_id")
    private Payment payment_fk; // 결제 - 구매내역 (단방향)

    @OneToMany(mappedBy = "purchaseDetails_fk")
    private List<PayrollTemplate> payrollTemplateList = new ArrayList<>(); // 구매내역 - 급여명세서 양식(양방향)

}
