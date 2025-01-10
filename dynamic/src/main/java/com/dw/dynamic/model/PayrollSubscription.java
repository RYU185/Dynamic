package com.dw.dynamic.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@ToString
@Table(name = "payroll_subscription")
public class PayrollSubscription extends Product {

    @Id
    @Column(name = "sub_payroll_name")
    private String subPayrollName;



}
