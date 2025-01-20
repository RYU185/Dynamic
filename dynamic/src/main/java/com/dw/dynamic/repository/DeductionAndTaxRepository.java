package com.dw.dynamic.repository;

import com.dw.dynamic.model.DeductionAndTax;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeductionAndTaxRepository extends JpaRepository<DeductionAndTax, String> {
    public List<DeductionAndTax> findByNameLike(String name);
}
