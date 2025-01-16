package com.dw.dynamic.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PayrollSubscriptionsEnrollmentAndIncomeDTO {

    private String title;
    private Long enrollment;
    private Double income;

}
