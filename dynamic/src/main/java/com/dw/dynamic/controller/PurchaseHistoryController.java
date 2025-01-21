package com.dw.dynamic.controller;

import com.dw.dynamic.DTO.CartDTO;
import com.dw.dynamic.DTO.PurchaseHistoryDTO;
import com.dw.dynamic.model.Cart;
import com.dw.dynamic.model.PurchaseHistory;
import com.dw.dynamic.service.PurchaseHistoryService;
import jakarta.servlet.http.HttpServletRequest;
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
    public ResponseEntity<List<PurchaseHistoryDTO>> getAllPurchaseHistorys(HttpServletRequest request) {
        return new ResponseEntity<>(
                purchaseHistoryService.getAllPurchaseHistorys(request),
                HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<PurchaseHistoryDTO> getPurchaseHistoryById(@PathVariable Long id,HttpServletRequest request) {
        return new ResponseEntity<>(
                purchaseHistoryService.getPurchaseHistoryById(id,request),
                HttpStatus.OK);
    }

    @GetMapping("/product-name/{productName}")
    public ResponseEntity<List<PurchaseHistoryDTO>> getPurchaseHistoryByProductName(@PathVariable String productName, HttpServletRequest request) {
        return new ResponseEntity<>(
                purchaseHistoryService.getPurchaseHistoryByProductName(productName,request),
                HttpStatus.OK);
    }
    @GetMapping("/product-id/{productId}")
    public ResponseEntity<PurchaseHistoryDTO> getPurchaseHistoryByProductId(@PathVariable String productId,HttpServletRequest request){
        return new ResponseEntity<>(
                purchaseHistoryService.getPurchaseHistoryByProductId(productId,request),
                HttpStatus.OK
        );
    }
    @PostMapping("/save/purchase-history-and-user-product")
    public ResponseEntity<List<PurchaseHistoryDTO>> savePurchaseHistoryAndUserProduct(@RequestBody List<CartDTO> carts, HttpServletRequest request){
        return new ResponseEntity<>(
                purchaseHistoryService.savePurchaseHistoryAndUserProduct(carts,request),
                HttpStatus.OK
        );
    }

}