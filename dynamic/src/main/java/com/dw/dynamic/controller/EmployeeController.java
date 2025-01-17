package com.dw.dynamic.controller;

import com.dw.dynamic.DTO.EmployeeDTO;
import com.dw.dynamic.model.Employee;
import com.dw.dynamic.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;
//
//    @GetMapping("/all")
//    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
//        return new ResponseEntity<>(
//                employeeService.getAllEmployees(),
//                HttpStatus.OK);
//    }
//
//    @GetMapping("/id/{id}")
//    public ResponseEntity<EmployeeDTO> getEmployeeById(Long id) {
//        return new ResponseEntity<>(
//                employeeService.getEmployeeById(id),
//                HttpStatus.OK);
//    }
//
//    @GetMapping("/name/{name}")
//    public ResponseEntity<List<EmployeeDTO>> getEmployeesByName(@PathVariable String name) {
//        return new ResponseEntity<>(
//                employeeService.getEmployeesByName(name),
//                HttpStatus.OK);
//    }
//    @GetMapping("/position/{position}")
//    public ResponseEntity<List<EmployeeDTO>> getEmployeesByPosition(@PathVariable String position) {
//        return new ResponseEntity<>(
//                employeeService.getEmployeesByPosition(position),
//                HttpStatus.OK);
//    }
//
    @PostMapping("/save")
    public ResponseEntity<EmployeeDTO> saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        return new ResponseEntity<>(
                employeeService.saveEmployee(employeeDTO),
                HttpStatus.OK);
    }
//    @PostMapping("")
//    public ResponseEntity<Employee>deleteEmployee() {
//        return new ResponseEntity<>(
//                employeeService.deleteEmployee(),
//                HttpStatus.OK);
//    }
}
