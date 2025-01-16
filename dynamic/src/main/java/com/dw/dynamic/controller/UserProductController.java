package com.dw.dynamic.controller;

import com.dw.dynamic.DTO.UserProductDTO;
import com.dw.dynamic.service.UserProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/apii/user-product")
public class
UserProductController {
    @Autowired
    UserProductService userProductService;

    @GetMapping("/all")
    public ResponseEntity<List<UserProductDTO>> getAllUserProduct(){
        return new ResponseEntity<>(
                UserProductService.getAllUserProduct(),
                HttpStatus.OK);
    }
    @GetMapping("/id/{id}")
    public ResponseEntity<UserProductDTO> getUserProductById(@PathVariable Long id) {
        return new ResponseEntity<>(
                UserProductService.getUserProductById(id),
                HttpStatus.OK);
    }

    @GetMapping("/product-id/{product-name}")
    public ResponseEntity<List<UserProductDTO>> getUserProductByProductName(@PathVariable String name) {
        return new ResponseEntity<>(
                UserProductService.getUserProductByProductName(name),
                HttpStatus.OK);
    }
}



