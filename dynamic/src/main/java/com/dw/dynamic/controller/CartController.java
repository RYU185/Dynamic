package com.dw.dynamic.controller;

import com.dw.dynamic.DTO.CartDTO;
import com.dw.dynamic.service.CartService;
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
    public ResponseEntity<List<CartDTO>> getAllCarts() {
        return new ResponseEntity<>(
                cartService.getAllCarts(),
                HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<CartDTO> getCartById(@PathVariable Long id) {
        return new ResponseEntity<>(
                cartService.getCartById(id),
                HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<CartDTO> saveCart(@RequestBody CartDTO cartDTO) {
        return new ResponseEntity<>(
                cartService.saveCart(cartDTO),
                HttpStatus.CREATED);
    }

    @PostMapping("/id/delete/{id}")
    public ResponseEntity<String> deleteCart(@RequestBody Long id) {
        return new ResponseEntity<>(
                cartService.deleteCart(id),
                HttpStatus.OK);
    }
}