package com.dw.dynamic.controller;

import com.dw.dynamic.model.DeductionAndTax;
import com.dw.dynamic.service.DeductionAndTaxService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/deduction-and-tax")
public class DeductionAndTaxController {
    @Autowired
    DeductionAndTaxService deductionAndTaxService;

    @GetMapping("/all")
    public ResponseEntity<List<DeductionAndTax>> getAllDeductionAndTaxs() {
        return new ResponseEntity<>(
                deductionAndTaxService.getAllDeductionAndTaxs(),
                HttpStatus.OK);
    }
    @GetMapping("/id/{id}")
    public ResponseEntity<DeductionAndTax> getDeductionAndTaxById(@PathVariable String id) {
        return new ResponseEntity<>(
                deductionAndTaxService.getDeductionAndTaxById(id),
                HttpStatus.OK);
    }
    @GetMapping("/id/{name}")
    public ResponseEntity<List<DeductionAndTax>> getDeductionAndTaxByName(@PathVariable String name) {
        return new ResponseEntity<>(
                deductionAndTaxService.getDeductionAndTaxByName(name),
                HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<DeductionAndTax> saveDeductionAndTax(@RequestBody DeductionAndTax deductionAndTax, HttpServletRequest request) {
        return new ResponseEntity<>(
                deductionAndTaxService.saveDeductionAndTax(deductionAndTax,request),
                HttpStatus.CREATED);
    }
    @DeleteMapping("/name/{name}")
    public ResponseEntity<String> deleteleDeductionAndTax(@PathVariable String name,HttpServletRequest request) {
        return new ResponseEntity<>(
                deductionAndTaxService.deleteDeductionAndTax(name, request),
                HttpStatus.OK);
    }
}
