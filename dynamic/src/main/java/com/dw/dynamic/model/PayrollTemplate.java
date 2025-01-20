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

    @Column(name="is_active")
    private Boolean isActive = true;

    @ManyToMany
    @JoinTable(name = "template_detail",
    joinColumns = @JoinColumn(name = "payroll_template"),
    inverseJoinColumns = @JoinColumn(name = "deduction_and_taxname"))
    private List<DeductionAndTax> deductionAndTax;

    @OneToOne
    @JoinColumn(name = "freelancer")
    private Freelancer freeLancer;

    @OneToOne(mappedBy = "payrollTemplate_fk")
    private Employee employee;

    public PayrollTemplateDTO toDTO(){
       List<String> deductionAndTaxName = new ArrayList<>();
       for (DeductionAndTax data : deductionAndTax) {
           deductionAndTaxName.add(data.getName());
       }
        EmployeeDTO employeeDTO= this.employee !=null ?this.employee.toDTO() : null;

       return  new PayrollTemplateDTO(
               this.id,
               this.startPayrollPeriod,
               this.lastPayrollPeriod,
               this.paymentDate,
               this.salary,
               this.bonus,
               this.mealAllowance,
               this.transportAllowance,
               this.otherAllowance,
               deductionAndTaxName,
               this.freeLancer.getName()
       );

    }

}
