package com.dw.dynamic.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "payroll_template")
public class PayrollTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_payroll_period",nullable = false)
    private LocalDate startPayrollPeriod;  // 급여 측정 시작 날짜

    @Column(name = "last_payroll_period",nullable = false)
    private LocalDate lastPayrollPeriod; // 급여 측정 끝나는 날짜

    @Column(name = "payment_date",nullable = false)
    private LocalDate paymentDate;

    @Column(name = "salary",nullable = false)
    private Long salary;// 기본급

    @Column(name = "bonus")
    private Long bonus;  // 인센

    @Column(name = "meal_allowance")
    private Long mealAllowance; // 식대

    @Column(name="transport_allowance")
    private Long transportAllowance; // 교통비

    @Column(name = "other_allowance")
    private  Long otherAllowance; // 그 외(야간, 연장, 휴일)

    @ManyToMany
    @JoinTable(name = "template_detail",
    joinColumns = @JoinColumn(name = "payroll_template"),
    inverseJoinColumns = @JoinColumn(name = "deduction_name"))
    private List<Deduction> deduction;

    @OneToOne
    @JoinColumn(name = "freelancer")
    private FreeLancer freeLancer;




}
