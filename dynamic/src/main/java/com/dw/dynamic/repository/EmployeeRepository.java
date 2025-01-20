package com.dw.dynamic.repository;

import com.dw.dynamic.model.Employee;
import com.dw.dynamic.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {

    public List<Employee> findByUser(User user);

    public List<Employee> findByNameLike(String name);
    public List<Employee> findByPosition(String position);
}
