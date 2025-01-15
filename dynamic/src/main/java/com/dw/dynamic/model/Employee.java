package com.dw.dynamic.model;

import com.dw.dynamic.DTO.EmployeeDTO;
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

    @Column(name="is_active")
    private Boolean isActive = true;

    @ManyToOne
    @JoinColumn(name = "user_name")
    private User user; // 단방향

    @OneToOne
    @JoinColumn(name = "payroll_template")
    private PayrollTemplate payrollTemplate_fk;

    public EmployeeDTO toDTO(){
        return new EmployeeDTO(
                this.id,
                this.name,
                this.department,
                this.position,
                this.hireDate,
                this.phoneNumber,
                this.user.getUserName(),
                this.payrollTemplate_fk.getId()
        );
    }
}
