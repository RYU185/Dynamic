package com.dw.dynamic.service;

import com.dw.dynamic.DTO.EmployeeDTO;
import com.dw.dynamic.exception.InvalidRequestException;
import com.dw.dynamic.exception.PermissionDeniedException;
import com.dw.dynamic.exception.ResourceNotFoundException;
import com.dw.dynamic.exception.UnauthorizedUserException;
import com.dw.dynamic.model.Employee;
import com.dw.dynamic.model.User;
import com.dw.dynamic.repository.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PayrollTemplateRepository payrollTemplateRepository;

    @Autowired
    DeductionRepository deductionRepository;

    @Autowired
    FreelancerRepository freelancerRepository;

//    public List<EmployeeDTO> getAllEmployeesByAdmin(HttpServletRequest request){  // 관리자가 전체 직원 조회
//        User currentUser = userService.getCurrentUser(request);
//        if (!currentUser.getAuthority().getAuthorityName().equals("ADMIN")){
//            throw new PermissionDeniedException("권한이 없습니다");
//        }
//        return employeeRepository.findAll().stream().map(Employee::toDTO).toList();
//
//    }
//    public List<EmployeeDTO> getAllEmployees(HttpServletRequest request){
//        User currentUser = userService.getCurrentUser(request);
//        try {
//            if (employeeRepository.findByUser(currentUser).isEmpty()){
//                throw new ResourceNotFoundException("작성한 직원 정보가 없습니다");
//            }else {
//                return employeeRepository.findByUser(currentUser).stream()
//                        .map(Employee::toDTO).toList();
//            }
//        }catch (InvalidRequestException e){
//            throw new InvalidRequestException("정상적인 요청이 아닙니다");
//        }
//
//    }
//    public EmployeeDTO getEmployeeById(Long id, HttpServletRequest request){
//        User currentUser = userService.getCurrentUser(request);
//        Employee employee= employeeRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("존재하지 않은 직원ID입니다."));
//        if(employee.getUser().equals(currentUser)){
//            return employee.toDTO();
//        }else {
//            throw new UnauthorizedUserException("해당 직원ID에 대한 조회 권한이 없습니다");
//        }
//    }
//    public List<EmployeeDTO> getEmployeesByName(String name, HttpServletRequest request){
//        User currentUser = userService.getCurrentUser(request);
//
//        List<Employee> employee= employeeRepository.findByNameLike("%"+name+"%");
//        if(employee.stream().map(Employee::getUser).equals(currentUser)){
//            return employee.stream().map(Employee::toDTO).toList();
//        }else {
//            throw new ResourceNotFoundException("해당 이름으로 조회되는 직원이 없습니다");
//        }
//    }
//    public List<EmployeeDTO> getEmployeesByPosition(String position,HttpServletRequest request){
//        User currentUser = userService.getCurrentUser(request);
//        try {
//            List<Employee> employee= employeeRepository.findByPostion(position);
//            if (employee.stream().map(Employee::getUser).equals(currentUser)){
//                return employee.stream().map(Employee::toDTO).toList();
//            }else {
//                throw new ResourceNotFoundException("해당 직위로 조회되는 직원이 없습니다");
//            }
//        }catch (InvalidRequestException e){
//            throw new InvalidRequestException("올바른 직위를 작성해주십시오");
//        }
//    }
    public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO){
        return employeeRepository.findById(employeeDTO.getId())
                .map((employee) -> {
                    return employeeRepository.save(employee).toDTO();
                })
                .orElseGet(() -> {
                    Employee employee = new Employee(
                            null,
                            employeeDTO.getName(),
                            employeeDTO.getDepartment(),
                            employeeDTO.getPosition(),
                            employeeDTO.getHireDate(),
                            employeeDTO.getPhoneNumber(),
                            true,
                            userRepository.findById(employeeDTO.getUserName()).orElseThrow(()->new ResourceNotFoundException("유저ID 오류")),
                            payrollTemplateRepository.findById(employeeDTO.getPayrollTemplateId()).orElseThrow(()->new ResourceNotFoundException("존재하지 않은 급여명세서 양식입니다"))
                    );
                    return employeeRepository.save(employee).toDTO();
                });
    }
}
