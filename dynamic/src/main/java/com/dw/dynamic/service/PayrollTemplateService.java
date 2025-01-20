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
        if (payrollTemplateDTO.getId() != null) {
            return payrollTemplateRepository.findById(payrollTemplateDTO.getId())
                    .map(payrollTemplate -> {
                        // 기존 PayrollTemplate 업데이트 후 저장
                        payrollTemplate.setStartPayrollPeriod(payrollTemplateDTO.getStartPayrollPeriod());
                        payrollTemplate.setLastPayrollPeriod(payrollTemplateDTO.getLastPayrollPeriod());
                        payrollTemplate.setPaymentDate(payrollTemplateDTO.getPaymentDate());
                        payrollTemplate.setSalary(payrollTemplateDTO.getSalary());
                        payrollTemplate.setBonus(payrollTemplateDTO.getBonus());
                        payrollTemplate.setMealAllowance(payrollTemplateDTO.getMealAllowance());
                        payrollTemplate.setTransportAllowance(payrollTemplateDTO.getTransportAllowance());
                        payrollTemplate.setOtherAllowance(payrollTemplateDTO.getOtherAllowance());
                        payrollTemplate.setFreeLancer(
                                freelancerRepository.findById(payrollTemplateDTO.getFreeLancerName())
                                        .orElseThrow(() -> new InvalidRequestException("3.3% 여부를 작성해주세요"))
                        );
                        // 기존 데이터를 업데이트한 후 저장
                        return payrollTemplateRepository.save(payrollTemplate).toDTO();
                    })
                    .orElseThrow(() -> new InvalidRequestException("해당 PayrollTemplate이 존재하지 않습니다."));
        } else {
            // ID가 없는 경우, 새로운 PayrollTemplate 생성
            PayrollTemplate payrollTemplate = new PayrollTemplate(
                    null, // ID는 null로 두어 자동 생성되도록 함
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
                    freelancerRepository.findById(payrollTemplateDTO.getFreeLancerName())
                            .orElseThrow(() -> new InvalidRequestException("3.3% 여부를 작성해주세요")),
                    null
            );
            return payrollTemplateRepository.save(payrollTemplate).toDTO();
        }
    }
}
