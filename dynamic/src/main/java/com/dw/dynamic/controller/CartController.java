package com.dw.dynamic.controller;

import com.dw.dynamic.DTO.CartDTO;
import com.dw.dynamic.service.CartService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    CartService cartService;

    @GetMapping("/all")
    public ResponseEntity<List<CartDTO>> getAllCarts(HttpServletRequest request) {
        return new ResponseEntity<>(
                cartService.getAllCarts(request),
                HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<CartDTO> getCartById(@PathVariable Long id,HttpServletRequest request) {
        return new ResponseEntity<>(
                cartService.getCartById(id,request),
                HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<CartDTO> saveCart(@RequestBody CartDTO cartDTO,HttpServletRequest request) {
        return new ResponseEntity<>(
                cartService.saveCart(cartDTO,request),
                HttpStatus.CREATED);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<String> deleteCart(@PathVariable Long id, HttpServletRequest request) {
        return new ResponseEntity<>(
                cartService.deleteCart(id,request),
                HttpStatus.OK);
    }
}