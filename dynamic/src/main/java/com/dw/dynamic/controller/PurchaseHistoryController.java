package com.dw.dynamic.controller;

import com.dw.dynamic.DTO.PurchaseHistoryDTO;
import com.dw.dynamic.service.PurchaseHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchase-history")
public class PurchaseHistoryController {
    @Autowired
    private PurchaseHistoryService purchaseHistoryService;

    @GetMapping("/all")
    public ResponseEntity<List<PurchaseHistoryDTO>> getAllPurchaseHistorys() {
        return new ResponseEntity<>(
                purchaseHistoryService.getAllPurchaseHistorys(),
                HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<PurchaseHistoryDTO> getPurchaseHistoryById(@PathVariable Long id) {
        return new ResponseEntity<>(
                purchaseHistoryService.getPurchaseHistoryById(id),
                HttpStatus.OK);
    }

//    @GetMapping("/product-name/{product-name}")
//    public ResponseEntity<List<PurchaseHistoryDTO>> getPurchaseHistoryByProductName(String productName) {
//        return new ResponseEntity<>(
//                purchaseHistoryService.getPurchaseHistoryByProductName(productName),
//                HttpStatus.OK);
//    }

}