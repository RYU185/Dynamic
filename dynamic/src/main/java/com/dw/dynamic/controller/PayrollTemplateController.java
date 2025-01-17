package com.dw.dynamic.controller;

import com.dw.dynamic.DTO.PayrollTemplateDTO;
import com.dw.dynamic.service.PayrollTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payrolltemplate")
public class PayrollTemplateController {
    @Autowired
    PayrollTemplateService payrollTemplateService;
//
//    @GetMapping("/all")
//    public ResponseEntity<PayrollTemplateDTO> getAllPayrollTemplates() {
//        return new ResponseEntity<>(
//                payrollTemplateService.getAllPayrollTemplates(),
//                HttpStatus.OK);
//    }
//
//    @GetMapping("/id/{id}")
//    public ResponseEntity<PayrollTemplateDTO> getPayrollTemplateById(@PathVariable Long id) {
//        return new ResponseEntity<>(
//                payrollTemplateService.getPayrollTemplateById(id),
//                HttpStatus.OK);
//    }
//
    @PostMapping("/save")
    public ResponseEntity<PayrollTemplateDTO> savePayrollTemplate(@RequestBody PayrollTemplateDTO payrollTemplateDTO ) {
        return new ResponseEntity<>(
                payrollTemplateService.savePayrollTemplate(payrollTemplateDTO),
                HttpStatus.CREATED);
    }
//
//    @PostMapping("/delete/{id}")
//    public ResponseEntity<String> deletePayrollTemplate(@PathVariable Long id) {
//        return new ResponseEntity<>(
//                payrollTemplateService.deletePayrollTemplate(id),
//                HttpStatus.OK);
//    }
}
