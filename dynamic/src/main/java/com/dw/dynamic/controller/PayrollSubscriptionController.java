package com.dw.dynamic.controller;

import com.dw.dynamic.DTO.PayrollSubscriptionsEnrollmentAndIncomeDTO;
import com.dw.dynamic.model.PayrollSubscription;
import com.dw.dynamic.service.PayrollSubscriptionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payrollsubscription")
public class PayrollSubscriptionController {
    @Autowired
    PayrollSubscriptionService payrollSubscriptionService;

    @GetMapping("/all")
    public ResponseEntity<List<PayrollSubscription>> getAllPayrollSubscriptions(){
        return new ResponseEntity<>(
                payrollSubscriptionService.getAllPayrollSubscriptions(),
                HttpStatus.OK
        );
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<PayrollSubscription> getPayrollSubscriptionById(@PathVariable String id){
        return new ResponseEntity<>(
                payrollSubscriptionService.getPayrollSubscriptionById(id),
                HttpStatus.OK
        );
    }

}

