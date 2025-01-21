package com.dw.dynamic.service;

import com.dw.dynamic.DTO.EmployeeDTO;
import com.dw.dynamic.DTO.PayrollTemplateDTO;
import com.dw.dynamic.exception.InvalidRequestException;
import com.dw.dynamic.exception.PermissionDeniedException;
import com.dw.dynamic.exception.ResourceNotFoundException;
import com.dw.dynamic.model.*;
import com.dw.dynamic.repository.DeductionAndTaxRepository;
import com.dw.dynamic.repository.EmployeeRepository;
import com.dw.dynamic.repository.FreelancerRepository;
import com.dw.dynamic.repository.PayrollTemplateRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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


    public List<PayrollTemplateDTO> getAllPayrollTemplates(HttpServletRequest request) {
        User currentUser = userService.getCurrentUser(request);
        if (currentUser.getAuthority().getAuthorityName().equals("ADMIN")) {
            return payrollTemplateRepository.findAll().stream().map(PayrollTemplate::toDTO).toList();
        }

        try {
            if (employeeRepository.findByUser(currentUser).isEmpty()) {
                throw new ResourceNotFoundException("조회되는 직원이 없어, 급여명세서 양식 또한 없습니다");
            } else {
                return employeeRepository.findByUser(currentUser).stream().map(Employee::getPayrollTemplate_fk).map(PayrollTemplate::toDTO).toList();
            }
        } catch (InvalidRequestException e) {
            throw new InvalidRequestException("정상적인 요청이 아닙니다");
        }

    }

    public PayrollTemplateDTO getPayrollTemplateById(Long id) {
        return payrollTemplateRepository.findById(id).map(PayrollTemplate::toDTO).orElseThrow(() -> new InvalidRequestException("존재하지 않은 ID입니다"));
    }

    public PayrollTemplateDTO updatePayrollTemplate(PayrollTemplateDTO payrollTemplateDTO,HttpServletRequest request) {
        User currentUser = userService.getCurrentUser(request);
        if (currentUser==null){
            throw new PermissionDeniedException("로그인 후 사용 가능합니다");
        }

        PayrollTemplate payrollTemplate=payrollTemplateRepository.findById(payrollTemplateDTO.getId()).orElseThrow(()->new ResourceNotFoundException("조회되는 급여명세서Id가 없습니다"));
        if (!payrollTemplate.getEmployee().getUser().getUserName().equals(currentUser.getUserName())){
            throw new PermissionDeniedException("다른 유저의 급여명세서를 수정할 수 없습니다");
        }
        List<Employee> employee =  employeeRepository.findByUser(currentUser);

        payrollTemplate.setStartPayrollPeriod(payrollTemplateDTO.getStartPayrollPeriod());
        payrollTemplate.setLastPayrollPeriod(payrollTemplateDTO.getLastPayrollPeriod());
        payrollTemplate.setSalary(payrollTemplateDTO.getSalary());
        payrollTemplate.setBonus(payrollTemplateDTO.getBonus());
        payrollTemplate.setMealAllowance(payrollTemplateDTO.getMealAllowance());
        payrollTemplate.setTransportAllowance(payrollTemplateDTO.getTransportAllowance());
        payrollTemplate.setOtherAllowance(payrollTemplateDTO.getOtherAllowance());


            return payrollTemplateRepository.save(payrollTemplate).toDTO();
    }

}