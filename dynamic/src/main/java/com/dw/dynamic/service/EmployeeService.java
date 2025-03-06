package com.dw.dynamic.service;

import com.dw.dynamic.DTO.EmployeeDTO;
import com.dw.dynamic.DTO.SaveEmployeeWithTemplateDTO;
import com.dw.dynamic.DTO.PayrollTemplateDTO;
import com.dw.dynamic.exception.InvalidRequestException;
import com.dw.dynamic.exception.PermissionDeniedException;
import com.dw.dynamic.exception.ResourceNotFoundException;
import com.dw.dynamic.exception.UnauthorizedUserException;
import com.dw.dynamic.model.*;
import com.dw.dynamic.repository.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    UserService userService;


    @Autowired
    PayrollTemplateRepository payrollTemplateRepository;


    public List<EmployeeDTO> getAllEmployeesByAdmin(HttpServletRequest request) {  // 관리자가 전체 직원 조회
        User currentUser = userService.getCurrentUser(request);
        if (!currentUser.getAuthority().getAuthorityName().equals("ADMIN")) {
            throw new PermissionDeniedException("권한이 없습니다");
        }
        return employeeRepository.findAll().stream().map(Employee::toDTO).toList();

    }

    public List<EmployeeDTO> getAllEmployees(HttpServletRequest request) {
        User currentUser = userService.getCurrentUser(request);
        try {
            return employeeRepository.findByUser(currentUser).stream()
                    .map(Employee::toDTO).toList();
            }catch (InvalidRequestException e) {
            throw new InvalidRequestException("정상적인 요청이 아닙니다");
        }

    }

    public EmployeeDTO getEmployeeById(Long id, HttpServletRequest request) {
        User currentUser = userService.getCurrentUser(request);
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("존재하지 않은 직원ID입니다."));
        if (employee.getUser().getUserName().equals(currentUser.getUserName())||currentUser.getAuthority().getAuthorityName().equals("ADMIN")) {
            return employee.toDTO();
        } else {
            throw new UnauthorizedUserException("해당 직원ID에 대한 조회 권한이 없습니다");
        }
    }

    public List<EmployeeDTO> getEmployeesByName(String name, HttpServletRequest request) {
        User currentUser = userService.getCurrentUser(request);

        List<Employee> employee = employeeRepository.findByNameLike("%" + name + "%");
        List<EmployeeDTO> employeeDTOList = employee.stream().filter(employee1 -> employee1.getUser().getUserName().equals(currentUser.getUserName())).map(Employee::toDTO).toList();
        if (employeeDTOList.isEmpty()) {
            throw new ResourceNotFoundException("해당 이름으로 조회되는 직원이 없습니다");
        } else {
            return employeeDTOList;
        }
    }

    public List<EmployeeDTO> getEmployeesByPosition(String position, HttpServletRequest request) {
        User currentUser = userService.getCurrentUser(request);
            List<Employee> employee = employeeRepository.findByPosition(position);
            List<EmployeeDTO> employeeDTOList = employee.stream().filter(employee1 -> employee1.getUser().getUserName().equals(currentUser.getUserName())).map(Employee::toDTO).toList();

            if (employeeDTOList.isEmpty()) {
                throw new ResourceNotFoundException("해당 직위로 조회되는 직원이 없습니다");
            } else {
               return employeeDTOList;
            }
    }


    public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO, HttpServletRequest request){
        User currentUser = userService.getCurrentUser(request);
        if (currentUser==null){
            throw new PermissionDeniedException("로그인 후 직원 등록이 가능합니다");
        }
        try {
            Employee employee = new Employee(
                                null,
                                employeeDTO.getName(),
                    employeeDTO.getDepartment(),
                    employeeDTO.getPosition(),
                    employeeDTO.getHourlyRate(),
                    employeeDTO.getBirthday(),
                    employeeDTO.getHireDate(),
                    employeeDTO.getPhoneNumber(),
                    currentUser,
                                true,
                                false
                            );
            return employeeRepository.save(employee).toDTO();
        }catch (InvalidRequestException e){
            throw new InvalidRequestException("제목과 본문 모두 작성해주세요");
        }
    }


    public EmployeeDTO updateEmployee(EmployeeDTO employeeDTO, HttpServletRequest request){
        User currentUser = userService.getCurrentUser(request);

        Employee employee =   employeeRepository.findById(employeeDTO.getId()).orElseThrow(()-> new ResourceNotFoundException("존재하지 않은  ID입니다"));
        employee.setName(employeeDTO.getName());
        employee.setPosition(employeeDTO.getPosition());
        employee.setDepartment(employeeDTO.getDepartment());
        employee.setPhoneNumber(employee.getPhoneNumber());
        employee.setHourlyRate(employeeDTO.getHourlyRate());
        return employeeRepository.save(employee).toDTO();
    }


    public String deleteEmployee(Long id,HttpServletRequest request){
        User currentUser = userService.getCurrentUser(request);
        Employee employee = employeeRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("존재하지 않은 직원ID입니다"));
        if (!employee.getUser().getUserName().equals(currentUser.getUserName())){
            throw new PermissionDeniedException("본인 직원에 대한 정보만 조회가 가능합니다.");
        }
        employee.setIsActive(false);
        employeeRepository.save(employee);
        return  "정상 삭제되었습니다";
    }
    @Transactional
    public SaveEmployeeWithTemplateDTO saveFreePayrollTemplate(SaveEmployeeWithTemplateDTO saveEmployeeWithTemplateDTO, HttpServletRequest request){
        User currentUser = userService.getCurrentUser(request);
        if (!currentUser.getAuthority().getAuthorityName().equals("USER")&&!currentUser.getAuthority().getAuthorityName().equals("ADMIN")){
            throw new PermissionDeniedException("회원가입 후 이용이 가능합니다");
        }
        Long count = employeeRepository.findByUser(currentUser).stream().filter(employee -> employee.getFreeTemplate().booleanValue()).count();
        if (currentUser.getAuthority().getAuthorityName().equals("USER")&&count<5){
            Employee employee = new Employee(
                    null,
                    saveEmployeeWithTemplateDTO.getEmployeeDTO().getName(),
                    saveEmployeeWithTemplateDTO.getEmployeeDTO().getDepartment(),
                    saveEmployeeWithTemplateDTO.getEmployeeDTO().getPosition(),
                    saveEmployeeWithTemplateDTO.getEmployeeDTO().getHourlyRate(),
                    saveEmployeeWithTemplateDTO.getEmployeeDTO().getBirthday(),
                    saveEmployeeWithTemplateDTO.getEmployeeDTO().getHireDate(),
                    saveEmployeeWithTemplateDTO.getEmployeeDTO().getPhoneNumber(),
                    currentUser,
                    true,
                 true);
            EmployeeDTO employeeDTO = employeeRepository.save(employee).toDTO();
            PayrollTemplate payrollTemplate = new PayrollTemplate(
                    null,
                    saveEmployeeWithTemplateDTO.getPayrollTemplateDTO().getStartPayrollPeriod(),
                    saveEmployeeWithTemplateDTO.getPayrollTemplateDTO().getLastPayrollPeriod(),
                    saveEmployeeWithTemplateDTO.getPayrollTemplateDTO().getPaymentDate(),
                    true,
                    employee,
                    saveEmployeeWithTemplateDTO.getPayrollTemplateDTO().getTotalAmount(),
                    saveEmployeeWithTemplateDTO.getPayrollTemplateDTO().getFinalPayment());
            PayrollTemplateDTO payrollTemplateDTO= payrollTemplateRepository.save(payrollTemplate).toDTO();

            SaveEmployeeWithTemplateDTO saveEmployeeWithTemplateDTO1 = new SaveEmployeeWithTemplateDTO(
                    payrollTemplateDTO,
                    employeeDTO
            );
            return saveEmployeeWithTemplateDTO1;
        }else if (currentUser.getAuthority().getAuthorityName().equals("ADMIN")){
            Employee employee = new Employee(
                    null,
                    saveEmployeeWithTemplateDTO.getEmployeeDTO().getName(),
                    saveEmployeeWithTemplateDTO.getEmployeeDTO().getDepartment(),
                    saveEmployeeWithTemplateDTO.getEmployeeDTO().getPosition(),
                    saveEmployeeWithTemplateDTO.getEmployeeDTO().getHourlyRate(),
                    saveEmployeeWithTemplateDTO.getEmployeeDTO().getBirthday(),
                    saveEmployeeWithTemplateDTO.getEmployeeDTO().getHireDate(),
                    saveEmployeeWithTemplateDTO.getEmployeeDTO().getPhoneNumber(),
                    currentUser,
                    true,
                    true);
            EmployeeDTO employeeDTO = employeeRepository.save(employee).toDTO();
            PayrollTemplate payrollTemplate = new PayrollTemplate(
                    null,
                    saveEmployeeWithTemplateDTO.getPayrollTemplateDTO().getStartPayrollPeriod(),
                    saveEmployeeWithTemplateDTO.getPayrollTemplateDTO().getLastPayrollPeriod(),
                    saveEmployeeWithTemplateDTO.getPayrollTemplateDTO().getPaymentDate(),
                    true,
                    employee,
                    saveEmployeeWithTemplateDTO.getPayrollTemplateDTO().getTotalAmount(),
                    saveEmployeeWithTemplateDTO.getPayrollTemplateDTO().getFinalPayment());
            PayrollTemplateDTO payrollTemplateDTO= payrollTemplateRepository.save(payrollTemplate).toDTO();
            SaveEmployeeWithTemplateDTO saveEmployeeWithTemplateDTO1 = new SaveEmployeeWithTemplateDTO(
                    payrollTemplateDTO,
                    employeeDTO
            );
            return saveEmployeeWithTemplateDTO1;
        }else {
            throw new InvalidRequestException("무료 이용 횟수 5회를 초과하였습니다. 추가적인 사용을 원하시면 유료 서비스를 사용해주세요");
        }
    }

    public Long freeTemplateCount(HttpServletRequest request){
        User currentUser = userService.getCurrentUser(request);
       return employeeRepository.freeTemplateCount();
    }
}



