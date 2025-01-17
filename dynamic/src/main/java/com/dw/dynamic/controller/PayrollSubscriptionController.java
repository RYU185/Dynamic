package com.dw.dynamic.controller;

import com.dw.dynamic.DTO.PayrollSubscriptionsEnrollmentAndIncomeDTO;
import com.dw.dynamic.service.PayrollSubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payrollsubscription")
public class PayrollSubscriptionController {
//    @Autowired
//    PayrollSubscriptionService payrollSubscriptionService;
//
//    @GetMapping("/all")
//    public ResponseEntity<PayrollSubscriptionsEnrollmentAndIncomeDTO> getAllPayrollSubscriptions() {
//        return new ResponseEntity<>(
//                payrollSubscriptionService.getAllPayrollSubscriptions(),
//                HttpStatus.OK);
//    }
//
//    @PostMapping("/save")
//    public ResponseEntity<PayrollSubscriptionsEnrollmentAndIncomeDTO> savePayrollSubscription(@RequestBody PayrollSubscriptionsEnrollmentAndIncomeDTO payrollSubscriptionsEnrollmentAndIncomeDTO) {
//        return new ResponseEntity<>(
//                payrollSubscriptionService.savePayrollSubscription(payrollSubscriptionsEnrollmentAndIncomeDTO),
//                HttpStatus.OK);
//    }
//
//    @GetMapping("/id/{id}")
//    public ResponseEntity<PayrollSubscriptionsEnrollmentAndIncomeDTO> getPayrollSubscriptionById(@PathVariable Long id) {
//        return new ResponseEntity<>(
//                payrollSubscriptionService.getPayrollSubscriptionById(id),
//                HttpStatus.OK);
//    }
}

