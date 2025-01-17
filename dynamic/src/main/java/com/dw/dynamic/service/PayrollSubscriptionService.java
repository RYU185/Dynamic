package com.dw.dynamic.service;

import com.dw.dynamic.exception.PermissionDeniedException;
import com.dw.dynamic.exception.ResourceNotFoundException;
import com.dw.dynamic.model.PayrollSubscription;
import com.dw.dynamic.model.User;
import com.dw.dynamic.repository.PayrollSubscriptionRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PayrollSubscriptionService {
    @Autowired
    PayrollSubscriptionRepository payrollSubscriptionRepository;

    @Autowired
    UserService userService;

    public List<PayrollSubscription> getAllPayrollSubscriptions() {
        return payrollSubscriptionRepository.findAll().stream().toList();
    }

    public PayrollSubscription getPayrollSubscriptionById(String id){
        return payrollSubscriptionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 제품ID 입니다 : " + id));
    }

    public PayrollSubscription savePayrollSubscription(PayrollSubscription payrollSubscription, HttpServletRequest request){
        User currentUser = userService.getCurrentUser(request);
        if (!currentUser.getAuthority().getAuthorityName().equals("ADMIN")){
            throw new PermissionDeniedException("권한이 없습니다.");
        }
        return payrollSubscriptionRepository.findById(payrollSubscription.getId())
                .map(ps -> payrollSubscriptionRepository.save(payrollSubscription))
                .orElseThrow(()-> new ResourceNotFoundException("없는 제품 ID입니다"));
    }
}
