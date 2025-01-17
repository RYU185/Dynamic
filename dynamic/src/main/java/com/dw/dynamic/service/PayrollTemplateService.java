package com.dw.dynamic.service;

import com.dw.dynamic.DTO.PayrollTemplateDTO;
import com.dw.dynamic.exception.InvalidRequestException;
import com.dw.dynamic.model.PayrollTemplate;
import com.dw.dynamic.repository.DeductionRepository;
import com.dw.dynamic.repository.EmployeeRepository;
import com.dw.dynamic.repository.FreelancerRepository;
import com.dw.dynamic.repository.PayrollTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PayrollTemplateService {
    @Autowired
    PayrollTemplateRepository payrollTemplateRepository;

    @Autowired
    FreelancerRepository freelancerRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    DeductionRepository deductionRepository;

    public PayrollTemplateDTO savePayrollTemplate(PayrollTemplateDTO payrollTemplateDTO){
        return payrollTemplateRepository.findById(payrollTemplateDTO.getId())
                .map((payrollTemplate) -> {
                    return payrollTemplateRepository.save(payrollTemplate).toDTO();
                })
                .orElseGet(() -> {
                    PayrollTemplate payrollTemplate = new PayrollTemplate(
                            null,
                            payrollTemplateDTO.getStartPayrollPeriod(),
                            payrollTemplateDTO.getLastPayrollPeriod(),
                            payrollTemplateDTO.getPaymentDate(),
                            payrollTemplateDTO.getSalary(),
                            payrollTemplateDTO.getBonus(),
                            payrollTemplateDTO.getMealAllowance(),
                            payrollTemplateDTO.getTransportAllowance(),
                            payrollTemplateDTO.getOtherAllowance(),
                            true,
                            deductionRepository.findAll(),
                            freelancerRepository.findById(payrollTemplateDTO.getFreeLancerName()).orElseThrow(()->new InvalidRequestException("3.3% 여부를 작성해주세요")),
                            null
                    );
                    return payrollTemplateRepository.save(payrollTemplate).toDTO();
                });

    }
}
