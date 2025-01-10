package com.dw.dynamic.repository;

import com.dw.dynamic.model.UserProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<UserProduct,String> {
}
