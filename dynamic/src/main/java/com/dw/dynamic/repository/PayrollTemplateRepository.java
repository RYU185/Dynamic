package com.dw.dynamic.repository;

import com.dw.dynamic.model.PayrollTemplate;
import com.dw.dynamic.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PayrollTemplateRepository extends JpaRepository<PayrollTemplate,Long> {

    @Query("SELECT p FROM PayrollTemplate p WHERE MONTH(p.paymentDate) = :month AND p.employee.user = :user AND p.isActive = true")
    List<PayrollTemplate> findByPaymentDateAndUser(int month, User user);

    @Query("select p from PayrollTemplate p where p.employee.user=:user")
    List<PayrollTemplate> findByUser(User user);

    List<PayrollTemplate> findByEmployeeNameLike(String name);




}
