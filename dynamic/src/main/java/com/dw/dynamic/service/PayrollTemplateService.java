package com.dw.dynamic.service;

import com.dw.dynamic.DTO.PayrollTemplateDTO;
import com.dw.dynamic.exception.InvalidRequestException;
import com.dw.dynamic.exception.PermissionDeniedException;
import com.dw.dynamic.exception.ResourceNotFoundException;
import com.dw.dynamic.model.PayrollTemplate;
import com.dw.dynamic.model.User;
import com.dw.dynamic.repository.DeductionAndTaxRepository;
import com.dw.dynamic.repository.EmployeeRepository;
import com.dw.dynamic.repository.FreelancerRepository;
import com.dw.dynamic.repository.PayrollTemplateRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PayrollTemplateService {
    @Autowired
    PayrollTemplateRepository payrollTemplateRepository;

    @Autowired
    FreelancerRepository freelancerRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    DeductionAndTaxRepository deductionAndTaxRepository;

    @Autowired
    UserService userService;


    public List<PayrollTemplateDTO> getAllPayrollTemplates(HttpServletRequest request){
        User currentUser = userService.getCurrentUser(request);
        try {
            if (payrollTemplateRepository.findByUser(currentUser).isEmpty()){
                throw new ResourceNotFoundException("작성한 급여명세서 양식이 없습니다");
            }else {
                return payrollTemplateRepository.findByUser(currentUser).stream()
                        .map(PayrollTemplate::toDTO).toList();
            }
        }catch (InvalidRequestException e){
            throw new InvalidRequestException("정상적인 요청이 아닙니다");
        }
    }

    public PayrollTemplateDTO getPayrollTemplateById(Long id){
        return payrollTemplateRepository.findById(id).map(PayrollTemplate::toDTO).orElseThrow(()->new InvalidRequestException("존재하지 않은 ID입니다"));
    }

    @Transactional
    public PayrollTemplateDTO savePayrollTemplate(PayrollTemplateDTO payrollTemplateDTO) {
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
                }).orElseGet(() -> {
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
                            deductionAndTaxRepository.findAll(),
                            freelancerRepository.findById(payrollTemplateDTO.getFreeLancerName())
                                    .orElseThrow(() -> new InvalidRequestException("3.3% 여부를 작성해주세요")),
                            null);
                    return payrollTemplateRepository.save(payrollTemplate).toDTO();
                });
    }
    public String deletePayrollTemplate(Long employeeId,Long payrollTemplateId,HttpServletRequest request){
        User currentUser = userService.getCurrentUser(request);
        if (!currentUser.equals(employeeRepository.findById(employeeId))){
            throw new PermissionDeniedException("본인 직원에 대한 정보만 조회가 가능합니다.");
        }
        PayrollTemplate payrollTemplate = payrollTemplateRepository.findById(payrollTemplateId).orElseThrow(()-> new InvalidRequestException("존재하지 않은 ID입니다"));
        payrollTemplate.setIsActive(false);
        payrollTemplateRepository.save(payrollTemplate);
        return  "정상 삭제되었습니다";
    }
}
