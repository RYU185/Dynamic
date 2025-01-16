package com.dw.dynamic.controller;

import com.dw.dynamic.model.Authority;
import com.dw.dynamic.service.AuthorityService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/authority")
public class AuthorityController {
    @Autowired
    AuthorityService authorityService;

    @GetMapping("/all")
    public ResponseEntity<List<Authority>> getAllAuthoritys(HttpServletRequest request) {
        return new ResponseEntity<>(
                authorityService.getAllAuthoritys(request),
                HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Authority> getAuthorityById(@PathVariable String id, HttpServletRequest request){
        return new ResponseEntity<>(
                authorityService.getAuthorityById(id,request),HttpStatus.OK
        );
    }

    @GetMapping("/name/{authorityName}")
    public ResponseEntity<List<Authority>>  getAuthorityByName(@PathVariable String authorityName,HttpServletRequest request){
        return new ResponseEntity<>(
                authorityService.getAuthoritysByName(authorityName,request),HttpStatus.OK
        );
    }
}
