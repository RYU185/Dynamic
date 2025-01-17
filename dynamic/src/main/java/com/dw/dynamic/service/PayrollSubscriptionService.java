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
                .orElseThrow(() -> new ResourceNotFoundException("존재하지 않는 제품ID 입니다 : " + id));
    }

}
