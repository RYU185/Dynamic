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
    EmployeeRepository employeeRepository;



    @Autowired
    UserService userService;


    public List<PayrollTemplateDTO> getAllPayrollTemplates(HttpServletRequest request) {
        User currentUser = userService.getCurrentUser(request);
        if (currentUser.getAuthority().getAuthorityName().equals("ADMIN")) {
            return payrollTemplateRepository.findAll().stream().map(PayrollTemplate::toDTO).toList();
        }
        try {
            if (employeeRepository.findByUser(currentUser).isEmpty()) {
                throw new ResourceNotFoundException("등록한 직원이 없어, 급여명세서 양식 또한 없습니다");
            } else {
                return payrollTemplateRepository.findByUser(currentUser).stream().map(PayrollTemplate::toDTO).toList();
            }
        } catch (InvalidRequestException e) {
            throw new InvalidRequestException("정상적인 요청이 아닙니다");
        }

    }

    public PayrollTemplateDTO getPayrollTemplateById(Long id) {
        return payrollTemplateRepository.findById(id).map(PayrollTemplate::toDTO).orElseThrow(() -> new InvalidRequestException("존재하지 않은 ID입니다"));
    }


    public  PayrollTemplateDTO savePayrollTemplate(PayrollTemplateDTO payrollTemplateDTO, HttpServletRequest request){
        User currentUser = userService.getCurrentUser(request);
        if (currentUser==null){
            throw new PermissionDeniedException("로그인 후 직원 등록이 가능합니다");
        }
        try {
            PayrollTemplate payrollTemplate = new PayrollTemplate(
                    null,
                    payrollTemplateDTO.getStartPayrollPeriod(),
                    payrollTemplateDTO.getLastPayrollPeriod(),
                    payrollTemplateDTO.getPaymentDate(),
                    true,
                    employeeRepository.findById(payrollTemplateDTO.getEmployeeId()).orElseThrow(()->new ResourceNotFoundException("존재하지 않은 게시판ID입니다")),
                    payrollTemplateDTO.getTotalAmount(),
                    payrollTemplateDTO.getFinalPayment()
            );

            return payrollTemplateRepository.save(payrollTemplate).toDTO();
        }catch (InvalidRequestException e){
            throw new InvalidRequestException("시작일, 종료일, 지급일 모두 작성해주세요");
        }

    }
     public List<PayrollTemplateDTO> getPayrollTemplateByPaymentDate(int month,HttpServletRequest request){
         User currentUser = userService.getCurrentUser(request);

        if (payrollTemplateRepository.findByPaymentDateAndUser(month,currentUser).isEmpty()){
            throw new ResourceNotFoundException("해당월로 발급하신 급여명세서는 없습니다");
        }
             return  payrollTemplateRepository.findByPaymentDateAndUser(month,currentUser).stream().map(PayrollTemplate::toDTO).toList();


     }

    public List<PayrollTemplateDTO> getPayrollTemplateByName(String name,HttpServletRequest request){
        User currentUser = userService.getCurrentUser(request);
        List<PayrollTemplate> payrollTemplate = payrollTemplateRepository.findByEmployeeNameLike("%" + name + "%");
        List<PayrollTemplateDTO> payrollTemplateDTOList = payrollTemplate.stream().filter(payrollTemplate1 -> payrollTemplate1.getEmployee().getUser().getUserName().equals(currentUser.getUserName())).map(PayrollTemplate::toDTO).toList();
        if (payrollTemplateDTOList.isEmpty()) {
            throw new ResourceNotFoundException("해당 이름으로 조회되는 직원이 없습니다");
        } else {
            return payrollTemplateDTOList
                    ;
        }

    }


}