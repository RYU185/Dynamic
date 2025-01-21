package com.dw.dynamic.DTO;


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

    private Long salary;// 기본급

    private Long bonus;  // 인센

    private Long mealAllowance; // 식대

    private Long transportAllowance; // 교통비

    private  Long otherAllowance; // 그 외(야간, 연장, 휴일)

    private List<String> deductionAndTax;

    private String freeLancerName;


}
