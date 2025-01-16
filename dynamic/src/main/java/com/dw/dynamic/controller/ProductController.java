package com.dw.dynamic.controller;

import com.dw.dynamic.DTO.CategoryEnrollmentAndIncomeDTO;
import com.dw.dynamic.DTO.CourseEnrollmentAndIncomeDTO;
import com.dw.dynamic.DTO.PayrollSubscriptionsEnrollmentAndIncomeDTO;
import com.dw.dynamic.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    ProductService productService;


    @GetMapping("payrollsubscription/enrollments-and-income")
    public ResponseEntity<List<PayrollSubscriptionsEnrollmentAndIncomeDTO>> etPayrollSubscriptionsEnrollmentsAndIncomes(HttpServletRequest request){
        return new ResponseEntity<>(
                productService.getPayrollSubscriptionsEnrollmentsAndIncomes(request),
                HttpStatus.OK
        );
    }

    @GetMapping("category/enrollments-and-income")
    public ResponseEntity<List<CategoryEnrollmentAndIncomeDTO>> getCategoryEnrollmentsAndIncomes(HttpServletRequest request){
        return new ResponseEntity<>(
                productService.getCategoryEnrollmentsAndIncomes(request),
                HttpStatus.OK
        );
    }

    @GetMapping("courses/enrollments-and-income")
    public ResponseEntity<List<CourseEnrollmentAndIncomeDTO>> getCoursesEnrollmentsAndIncomes(HttpServletRequest request){
        return new ResponseEntity<>(
                productService.getCoursesEnrollmentsAndIncomes(request),
                HttpStatus.OK
        );
    }
}
