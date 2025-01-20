package com.dw.dynamic.repository;

import com.dw.dynamic.model.PayrollTemplate;
import com.dw.dynamic.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PayrollTemplateRepository extends JpaRepository<PayrollTemplate,Long> {
    public List<PayrollTemplate> findByUser(User user);
}
