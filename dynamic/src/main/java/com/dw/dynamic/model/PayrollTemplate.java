package com.dw.dynamic.model;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "payroll_template")
public class PayrollTemplate {
    @Id
    @Column(name = "id")
    private String id;

    @OneToOne
    @JoinColumn(name = "deduction")
    private Deduction deduction;

    @ManyToOne
    @JoinColumn(name = "purchase_details_id")
    private PurchaseDetails purchaseDetails_fk; // 급여명세서 양식 - 구매내역 (양방향)




}
