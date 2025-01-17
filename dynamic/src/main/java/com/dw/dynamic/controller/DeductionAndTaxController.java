package com.dw.dynamic.controller;

import com.dw.dynamic.model.DeductionAndTax;
import com.dw.dynamic.service.DeductionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/deduction-and-tax")
public class DeductionAndTaxController {
//    @Autowired
//    DeductionService deductionService;
//
//    @GetMapping("/all")
//    public ResponseEntity<List<DeductionAndTax> getAllDeductionAndTaxs() {
//        return new ResponseEntity<>(
//                deductionService.getAllDeductionAndTaxs(),
//                HttpStatus.OK);
//    }
//    @GetMapping("/id/{id}")
//    public ResponseEntity<DeductionAndTax> getDeductionAndTaxById(@PathVariable Long id) {
//        return new ResponseEntity<>(
//                deductionService.getDeductionAndTaxById(id),
//                HttpStatus.OK);
//    }
//    @GetMapping("/id/{name}")
//    public ResponseEntity<List<DeductionAndTax>> getDeductionAndTaxByNames(@PathVariable String name) {
//        return new ResponseEntity<>(
//                deductionService.getDeductionAndTaxByNames(name),
//                HttpStatus.OK);
//    }
//
//    @PostMapping("/save")
//    public ResponseEntity<DeductionAndTax> saveDeductionAndTax(@RequestBody DeductionAndTax deductionAndTax) {
//        return new ResponseEntity<>(
//                deductionService.saveDeductionAndTax(deductionAndTax),
//                HttpStatus.CREATED);
//    }
//    @DeleteMapping("/name/{name}")
//    public ResponseEntity<DeductionAndTax> deleteleDeductionAndTax(@PathVariable String name) {
//        return new ResponseEntity<>(
//                deductionService.deleteleDeductionAndTax(name),
//                HttpStatus.OK);
//    }
}
