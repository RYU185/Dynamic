package com.dw.dynamic.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "department")
    private String department; // 부서명

    @Column(name = "position")
    private String position; // 직위

    @Column(name = "hire_date", nullable = false, updatable = false)
    private LocalDate hireDate; // 입사일

    @Column(name = "phone_number")
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // 단방향

    @OneToOne
    @JoinColumn(name = "payroll_template")
    private PayrollTemplate payrollTemplate;

}
