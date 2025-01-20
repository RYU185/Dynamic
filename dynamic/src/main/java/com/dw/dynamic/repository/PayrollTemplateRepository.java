package com.dw.dynamic.repository;

import com.dw.dynamic.model.PayrollTemplate;
import com.dw.dynamic.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PayrollTemplateRepository extends JpaRepository<PayrollTemplate,Long> {



}
