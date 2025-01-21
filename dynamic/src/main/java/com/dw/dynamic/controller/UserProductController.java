package com.dw.dynamic.controller;

import com.dw.dynamic.DTO.UserProductDTO;
import com.dw.dynamic.model.UserProduct;
import com.dw.dynamic.service.UserProductService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-product")
public class
UserProductController {
    @Autowired
    UserProductService userProductService;

    @GetMapping("/all")
    public ResponseEntity<List<UserProductDTO>> getAllUserProducts(HttpServletRequest request){
        return new ResponseEntity<>(
                userProductService.getAllUserProducts(request),
                HttpStatus.OK);
    }
    @GetMapping("/id/{id}")
    public ResponseEntity<UserProductDTO> getUserProductById(@PathVariable Long id, HttpServletRequest request) {
        return new ResponseEntity<>(
                userProductService.getUserProductById(id, request),
                HttpStatus.OK);
    }

    @GetMapping("/product-id/{productId}")
    public ResponseEntity<UserProductDTO> getUserProductByProductId(@PathVariable String productId, HttpServletRequest request){
        return new ResponseEntity<>(
                userProductService.getUserProductByProductId(productId, request),
                HttpStatus.OK
        );
    }

    @GetMapping("/product-name/{productName}")
    public ResponseEntity<List<UserProductDTO>> getUserProductByProductName(@PathVariable String productName, HttpServletRequest request) {
        return new ResponseEntity<>(
                userProductService.getUserProductByProductName(productName, request),
                HttpStatus.OK);
    }
}

