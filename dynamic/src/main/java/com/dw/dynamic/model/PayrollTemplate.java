package com.dw.dynamic.model;

import com.dw.dynamic.DTO.EmployeeDTO;
import com.dw.dynamic.DTO.PayrollTemplateDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
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

    @Column(name="is_active")
    private Boolean isActive = true;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column(name = "total_amount",nullable = false)
    private Long totalAmount;

    @Column(name = "final_payment",nullable = false)
    private Long finalPayment;


    public PayrollTemplateDTO toDTO(){


       return  new PayrollTemplateDTO(
               this.id,
               this.startPayrollPeriod,
               this.lastPayrollPeriod,
               this.paymentDate,
                this.isActive,
                this.employee.getId(),
               this.employee.getName(),
               this.employee.getPosition(),
               this.totalAmount,
               this.finalPayment

       );

    }

}
