package com.dw.dynamic.controller;

import com.dw.dynamic.DTO.EmployeeDTO;
import com.dw.dynamic.DTO.SaveEmployeeWithTemplateDTO;
import com.dw.dynamic.service.EmployeeService;
import jakarta.servlet.http.HttpServletRequest;
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

    @GetMapping("/all-by-admin")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployeeByAdmin(HttpServletRequest request){
        return new ResponseEntity<>(
                employeeService.getAllEmployeesByAdmin(request),
                HttpStatus.OK
        );
    }

    @GetMapping("/all")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees(HttpServletRequest request) {
        return new ResponseEntity<>(
                employeeService.getAllEmployees(request),
                HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long id,HttpServletRequest request) {
        return new ResponseEntity<>(
                employeeService.getEmployeeById(id, request),
                HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<EmployeeDTO>> getEmployeesByName(@PathVariable String name,HttpServletRequest request) {
        return new ResponseEntity<>(
                employeeService.getEmployeesByName(name, request),
                HttpStatus.OK);
    }
    @GetMapping("/position/{position}")
    public ResponseEntity<List<EmployeeDTO>> getEmployeesByPosition(@PathVariable String position,HttpServletRequest request) {
        return new ResponseEntity<>(
                employeeService.getEmployeesByPosition(position,request),
                HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<SaveEmployeeWithTemplateDTO> saveEmployee(@RequestBody SaveEmployeeWithTemplateDTO saveEmployeeWithTemplateDTO,HttpServletRequest request) {
        return new ResponseEntity<>(
                employeeService.saveEmployee(saveEmployeeWithTemplateDTO,request),
                HttpStatus.OK);
    }
    @PostMapping("/delete/{id}")
    public ResponseEntity<String>deleteEmployee(@PathVariable Long id,HttpServletRequest request) {
        return new ResponseEntity<>(
                employeeService.deleteEmployee(id,request),
                HttpStatus.OK);
    }
    @PostMapping("/use/free-payrolltemplate")
    public ResponseEntity<SaveEmployeeWithTemplateDTO> saveFreePayrollTemplate(@RequestBody SaveEmployeeWithTemplateDTO saveEmployeeWithTemplateDTO, HttpServletRequest request){
        return new ResponseEntity<>(
                employeeService.saveFreePayrollTemplate(saveEmployeeWithTemplateDTO,request),
                HttpStatus.OK
        );
    }
}
