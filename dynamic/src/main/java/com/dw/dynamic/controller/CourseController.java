package com.dw.dynamic.controller;

import com.dw.dynamic.model.Course;
import com.dw.dynamic.model.Product;
import com.dw.dynamic.service.CourseService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/course")
public class CourseController {
    @Autowired
    CourseService courseService;

    @GetMapping("/all")
    public ResponseEntity<List<Course>> getAllCourses(){
        return new ResponseEntity<>(
                courseService.getAllCourses(),
                HttpStatus.OK);
    }

    @GetMapping("id/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable String id){
        return new ResponseEntity<>(
                courseService.getCourseById(id),
                HttpStatus.OK
        );
    }


    @GetMapping("/title/{title}")
    public ResponseEntity<List<Course>> getCoursesByTitle(@PathVariable String title){
        return new ResponseEntity<>(
                courseService.getCoursesByTitle(title),
                HttpStatus.OK
        );
    }

}
