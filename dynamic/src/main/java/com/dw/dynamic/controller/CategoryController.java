package com.dw.dynamic.controller;

import com.dw.dynamic.DTO.CategoryEnrollmentAndIncomeDTO;
import com.dw.dynamic.model.Category;
import com.dw.dynamic.service.CategoryService;
import jakarta.servlet.http.HttpServletRequest;
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
    public ResponseEntity<List<Category>> getAllCategorys(){
        return new ResponseEntity<>(
                categoryService.getAllCategorys(),
                HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable String id) {
        return new ResponseEntity<>(
                categoryService.getCategoryById(id),
                HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<Category> saveCategory(@RequestBody Category category, HttpServletRequest request) {
        return new ResponseEntity<>(
                categoryService.saveCategory(category, request),
                HttpStatus.CREATED);
    }

    @DeleteMapping("/name/{name}")
    public ResponseEntity<String> deleteCategory(@PathVariable String name, HttpServletRequest request) {
        return new ResponseEntity<>(
                categoryService.deleteCategory(name, request),
                HttpStatus.OK);
    }
}
