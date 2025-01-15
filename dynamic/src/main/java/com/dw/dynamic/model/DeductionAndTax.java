package com.dw.dynamic.model;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "deduction_and_tax")
public class DeductionAndTax {
    @Id
    @Column(name = "name", nullable = false, unique = true)
    private String name;// 공제 명칭

    @Column(name = "amount", nullable = false)
    private double amount; // 공제 항목별 금액



}
