package com.dw.dynamic.controller;

import com.dw.dynamic.DTO.CategoryEnrollmentAndIncomeDTO;
import com.dw.dynamic.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @GetMapping("/all")
    public ResponseEntity<List<CategoryEnrollmentAndIncomeDTO> getAllCategorys(){
        return new ResponseEntity<>(
                categoryService.getAllCategorys(),
                HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<CategoryEnrollmentAndIncomeDTO> getCategoryById(@PathVariable Long id) {
        return new ResponseEntity<>(
                categoryService.getCategoryById(id),
                HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<CategoryEnrollmentAndIncomeDTO> saveCategory(@RequestBody CategoryEnrollmentAndIncomeDTO categoryEnrollmentAndIncomeDTO) {
        return new ResponseEntity<>(
                categoryService.saveCategory(categoryEnrollmentAndIncomeDTO),
                HttpStatus.CREATED);
    }

    @DeleteMapping("/name/{name}")
    public ResponseEntity<CategoryEnrollmentAndIncomeDTO> deleteCategory(@PathVariable String name) {
        return new ResponseEntity<>(
                categoryService.deleteCategory(name),
                HttpStatus.OK);
    }
}
