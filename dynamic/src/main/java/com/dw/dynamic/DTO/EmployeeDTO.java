package com.dw.dynamic.DTO;


import com.dw.dynamic.model.PayrollTemplate;
import com.dw.dynamic.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EmployeeDTO {

    private Long id;

    private String name;

    private String department; // 부서명

    private String position; // 직위

    private LocalDate hireDate; // 입사일

    private String phoneNumber;

    private String userName; // 단방향

    private Long payrollTemplateId;
}
