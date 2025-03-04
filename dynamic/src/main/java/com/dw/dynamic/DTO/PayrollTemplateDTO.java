package com.dw.dynamic.DTO;


import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PayrollTemplateDTO {

    private Long id;

    private LocalDate startPayrollPeriod;  // 급여 측정 시작 날짜

    private LocalDate lastPayrollPeriod; // 급여 측정 끝나는 날짜

    private LocalDate paymentDate;

    private Boolean isActive;

    private Long employeeId;

    private String employeeName;

    private String position;

    private Long totalAmount;

    private Long finalPayment;




}
