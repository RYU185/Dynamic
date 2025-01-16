package com.dw.dynamic.controller;

import com.dw.dynamic.model.Authority;
import com.dw.dynamic.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/authority")
public class AuthorityController {
    @Autowired
    AuthorityService authorityService;

    @GetMapping("/all")
    public ResponseEntity<List<Authority>> getAllAuthoritys() {
        return new ResponseEntity<>(
                authorityService.getAllAuthoritys(),
                HttpStatus.OK);
    }
}
