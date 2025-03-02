package com.dw.dynamic.controller;

import com.dw.dynamic.DTO.PointDTO;
import com.dw.dynamic.service.PointService;
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
@RequestMapping("/api/point")
public class PointController {

    @Autowired
    PointService pointService;

    @GetMapping("/all")
    public ResponseEntity<List<PointDTO>> getAllPoints(HttpServletRequest request){
        return new ResponseEntity<>(pointService.getAllPoints(request), HttpStatus.OK);
    }
    @GetMapping("/id/{id}")
    public ResponseEntity<PointDTO> getPointById(@PathVariable Long id, HttpServletRequest request){
        return new ResponseEntity<>(pointService.getPointById(id,request), HttpStatus.OK);
    }
}
