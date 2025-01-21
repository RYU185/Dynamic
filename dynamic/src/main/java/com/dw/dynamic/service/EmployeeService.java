package com.dw.dynamic.service;

import com.dw.dynamic.DTO.EmployeeDTO;
import com.dw.dynamic.DTO.SaveEmployeeWithTemplateDTO;
import com.dw.dynamic.DTO.PayrollTemplateDTO;
import com.dw.dynamic.exception.InvalidRequestException;
import com.dw.dynamic.exception.PermissionDeniedException;
import com.dw.dynamic.exception.ResourceNotFoundException;
import com.dw.dynamic.exception.UnauthorizedUserException;
import com.dw.dynamic.model.Employee;
import com.dw.dynamic.model.PayrollTemplate;
import com.dw.dynamic.model.User;
import com.dw.dynamic.repository.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeParseException;
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
    DeductionAndTaxRepository deductionAndTaxRepository;

    @Autowired
    FreelancerRepository freelancerRepository;

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
            if (employeeRepository.findByUser(currentUser).isEmpty()) {
                throw new ResourceNotFoundException("작성한 직원 정보가 없습니다");
            } else {
                return employeeRepository.findByUser(currentUser).stream()
                        .map(Employee::toDTO).toList();
            }
        } catch (InvalidRequestException e) {
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
    @Transactional
    public SaveEmployeeWithTemplateDTO saveEmployee(SaveEmployeeWithTemplateDTO saveEmployeeWithTemplateDTO,HttpServletRequest request) {
        User currentUser = userService.getCurrentUser(request);
        if (currentUser==null){
            throw new PermissionDeniedException("로그인 후 직원 등록이 가능합니다");
        }
        try {
            return employeeRepository.findById(saveEmployeeWithTemplateDTO.getEmployeeDTO().getId())
                    .map((employee) -> {
                        EmployeeDTO employeeDTO = employeeRepository.save(employee).toDTO();
                        SaveEmployeeWithTemplateDTO saveEmployeeWithTemplateDTO1 = new SaveEmployeeWithTemplateDTO(
                                saveEmployeeWithTemplateDTO.getPayrollTemplateDTO(),
                                employeeDTO);
                        return saveEmployeeWithTemplateDTO1;
                    })
                    .orElseGet(() -> {
                        PayrollTemplate payrollTemplate = new PayrollTemplate(
                                null,
                                saveEmployeeWithTemplateDTO.getPayrollTemplateDTO().getStartPayrollPeriod(),
                                saveEmployeeWithTemplateDTO.getPayrollTemplateDTO().getLastPayrollPeriod(),
                                saveEmployeeWithTemplateDTO.getPayrollTemplateDTO().getPaymentDate(),
                                saveEmployeeWithTemplateDTO.getPayrollTemplateDTO().getSalary(),
                                saveEmployeeWithTemplateDTO.getPayrollTemplateDTO().getBonus(),
                                saveEmployeeWithTemplateDTO.getPayrollTemplateDTO().getMealAllowance(),
                                saveEmployeeWithTemplateDTO.getPayrollTemplateDTO().getTransportAllowance(),
                                saveEmployeeWithTemplateDTO.getPayrollTemplateDTO().getOtherAllowance(),
                                true,
                                deductionAndTaxRepository.findAll(),
                                freelancerRepository.findById(saveEmployeeWithTemplateDTO.getPayrollTemplateDTO().getFreeLancerName()).orElseThrow(() -> new InvalidRequestException("3.3% 여부를 작성해주세요")),
                                null);
                        PayrollTemplateDTO payrollTemplateDTO = payrollTemplateRepository.save(payrollTemplate).toDTO();
                        Employee employee = new Employee(
                                null,
                                saveEmployeeWithTemplateDTO.getEmployeeDTO().getName(),
                                saveEmployeeWithTemplateDTO.getEmployeeDTO().getDepartment(),
                                saveEmployeeWithTemplateDTO.getEmployeeDTO().getPosition(),
                                saveEmployeeWithTemplateDTO.getEmployeeDTO().getHireDate(),
                                saveEmployeeWithTemplateDTO.getEmployeeDTO().getPhoneNumber(),
                                true,
                                false,
                                currentUser,
                                payrollTemplate);
                        EmployeeDTO employeeDTO = employeeRepository.save(employee).toDTO();
                        SaveEmployeeWithTemplateDTO saveEmployeeWithTemplateDTO1 = new SaveEmployeeWithTemplateDTO(
                                payrollTemplateDTO,
                                employeeDTO
                        );
                        return saveEmployeeWithTemplateDTO1;
                    });
        }catch (InvalidRequestException e){
            throw new InvalidRequestException("직원 이름, 입사일은 필수 입력 사항입니다. 급여명세서 측정 날짜/지급일/기본급은 필수입력 사항입니다");
        }catch (DateTimeParseException e){
            throw new InvalidRequestException("날짜 입력이 올바르지 않습니다.");
        }

    }
    public String deleteEmployee(Long id,HttpServletRequest request){
        User currentUser = userService.getCurrentUser(request);
        Employee employee = employeeRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("존재하지 않은 직원ID입니다"));
        if (!employee.getUser().getUserName().equals(currentUser.getUserName())){
            throw new PermissionDeniedException("본인 직원에 대한 정보만 조회가 가능합니다.");
        }
        employee.getPayrollTemplate_fk().setIsActive(false);
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
            PayrollTemplate payrollTemplate = new PayrollTemplate(
                    null,
                    saveEmployeeWithTemplateDTO.getPayrollTemplateDTO().getStartPayrollPeriod(),
                    saveEmployeeWithTemplateDTO.getPayrollTemplateDTO().getLastPayrollPeriod(),
                    saveEmployeeWithTemplateDTO.getPayrollTemplateDTO().getPaymentDate(),
                    saveEmployeeWithTemplateDTO.getPayrollTemplateDTO().getSalary(),
                    saveEmployeeWithTemplateDTO.getPayrollTemplateDTO().getBonus(),
                    saveEmployeeWithTemplateDTO.getPayrollTemplateDTO().getMealAllowance(),
                    saveEmployeeWithTemplateDTO.getPayrollTemplateDTO().getTransportAllowance(),
                    saveEmployeeWithTemplateDTO.getPayrollTemplateDTO().getOtherAllowance(),
                    true,
                    deductionAndTaxRepository.findAll(),
                    freelancerRepository.findById(saveEmployeeWithTemplateDTO.getPayrollTemplateDTO().getFreeLancerName()) .orElseThrow(() -> new InvalidRequestException("3.3% 여부를 작성해주세요")),
                    null);
            PayrollTemplateDTO payrollTemplateDTO= payrollTemplateRepository.save(payrollTemplate).toDTO();
            Employee employee = new Employee(
                    null,
                    saveEmployeeWithTemplateDTO.getEmployeeDTO().getName(),
                    saveEmployeeWithTemplateDTO.getEmployeeDTO().getDepartment(),
                    saveEmployeeWithTemplateDTO.getEmployeeDTO().getPosition(),
                    saveEmployeeWithTemplateDTO.getEmployeeDTO().getHireDate(),
                    saveEmployeeWithTemplateDTO.getEmployeeDTO().getPhoneNumber(),
                    true,
                    true,
                    currentUser,
                    payrollTemplate);
            EmployeeDTO employeeDTO = employeeRepository.save(employee).toDTO();
            SaveEmployeeWithTemplateDTO saveEmployeeWithTemplateDTO1 = new SaveEmployeeWithTemplateDTO(
                    payrollTemplateDTO,
                    employeeDTO
            );
            return saveEmployeeWithTemplateDTO1;
        }else if (currentUser.getAuthority().getAuthorityName().equals("ADMIN")){
            PayrollTemplate payrollTemplate = new PayrollTemplate(
                    null,
                    saveEmployeeWithTemplateDTO.getPayrollTemplateDTO().getStartPayrollPeriod(),
                    saveEmployeeWithTemplateDTO.getPayrollTemplateDTO().getLastPayrollPeriod(),
                    saveEmployeeWithTemplateDTO.getPayrollTemplateDTO().getPaymentDate(),
                    saveEmployeeWithTemplateDTO.getPayrollTemplateDTO().getSalary(),
                    saveEmployeeWithTemplateDTO.getPayrollTemplateDTO().getBonus(),
                    saveEmployeeWithTemplateDTO.getPayrollTemplateDTO().getMealAllowance(),
                    saveEmployeeWithTemplateDTO.getPayrollTemplateDTO().getTransportAllowance(),
                    saveEmployeeWithTemplateDTO.getPayrollTemplateDTO().getOtherAllowance(),
                    true,
                    deductionAndTaxRepository.findAll(),
                    freelancerRepository.findById(saveEmployeeWithTemplateDTO.getPayrollTemplateDTO().getFreeLancerName()) .orElseThrow(() -> new InvalidRequestException("3.3% 여부를 작성해주세요")),
                    null);
            PayrollTemplateDTO payrollTemplateDTO= payrollTemplateRepository.save(payrollTemplate).toDTO();
            Employee employee = new Employee(
                    null,
                    saveEmployeeWithTemplateDTO.getEmployeeDTO().getName(),
                    saveEmployeeWithTemplateDTO.getEmployeeDTO().getDepartment(),
                    saveEmployeeWithTemplateDTO.getEmployeeDTO().getPosition(),
                    saveEmployeeWithTemplateDTO.getEmployeeDTO().getHireDate(),
                    saveEmployeeWithTemplateDTO.getEmployeeDTO().getPhoneNumber(),
                    true,
                    true,
                    currentUser,
                    payrollTemplate);
            EmployeeDTO employeeDTO = employeeRepository.save(employee).toDTO();
            SaveEmployeeWithTemplateDTO saveEmployeeWithTemplateDTO1 = new SaveEmployeeWithTemplateDTO(
                    payrollTemplateDTO,
                    employeeDTO
            );
            return saveEmployeeWithTemplateDTO1;
        }else {
            throw new InvalidRequestException("무료 이용 횟수 5회를 초과하였습니다. 추가적인 사용을 원하시면 유료 서비스를 사용해주세요");
        }
    }
}
