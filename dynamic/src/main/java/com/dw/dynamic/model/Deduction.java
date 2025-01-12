package com.dw.dynamic.model;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "deduction")
public class Deduction {
    @Id
    @Column(name = "insurance_name", nullable = false, unique = true)
    private String name;// 공제 명칭

    @Column(name = "insurance_amount", nullable = false)
    private Long amount; // 공제 항목별 금액

    @Column(name =  "tax")
    private Long tax; // 세금

}
