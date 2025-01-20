package com.dw.dynamic.controller;


import com.dw.dynamic.model.Freelancer;
import com.dw.dynamic.service.FreelancerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/freelancer")
public class FreelancerController {
    @Autowired
    FreelancerService freelancerService;

    @GetMapping("/all")
    public ResponseEntity<List<Freelancer>> getAllFreelancer() {
        return new ResponseEntity<>(
                freelancerService.getAllFreelancer(),
                HttpStatus.OK);
    }
}
