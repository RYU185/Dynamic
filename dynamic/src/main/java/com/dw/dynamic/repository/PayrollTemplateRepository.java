package com.dw.dynamic.repository;

import com.dw.dynamic.model.PayrollTemplate;
import com.dw.dynamic.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PayrollTemplateRepository extends JpaRepository<PayrollTemplate,Long> {

    @Query("SELECT p FROM PayrollTemplate p WHERE FUNCTION('MONTH', p.paymentDate) = :month AND p.isActive = true")
    public List<PayrollTemplate> findByPaymentDate(int month);





}
