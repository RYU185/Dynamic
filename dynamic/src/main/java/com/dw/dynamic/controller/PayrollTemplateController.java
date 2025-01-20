package com.dw.dynamic.controller;

import com.dw.dynamic.DTO.PayrollTemplateDTO;
import com.dw.dynamic.service.PayrollTemplateService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payrolltemplate")
public class PayrollTemplateController {
    @Autowired
    PayrollTemplateService payrollTemplateService;

    @GetMapping("/all")
    public ResponseEntity<List<PayrollTemplateDTO>> getAllPayrollTemplates(HttpServletRequest request) {
        return new ResponseEntity<>(
                payrollTemplateService.getAllPayrollTemplates(request),
                HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<PayrollTemplateDTO> getPayrollTemplateById(@PathVariable Long id) {
        return new ResponseEntity<>(
                payrollTemplateService.getPayrollTemplateById(id),
                HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<PayrollTemplateDTO> savePayrollTemplate(@RequestBody PayrollTemplateDTO payrollTemplateDTO ) {
        return new ResponseEntity<>(
                payrollTemplateService.savePayrollTemplate(payrollTemplateDTO),
                HttpStatus.CREATED);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<String> deletePayrollTemplate(@RequestParam Long employeeId, @RequestParam Long payrolltemplateId, HttpServletRequest request) {
        return new ResponseEntity<>(
                payrollTemplateService.deletePayrollTemplate(employeeId,payrolltemplateId,request),
                HttpStatus.OK);
    }
}
