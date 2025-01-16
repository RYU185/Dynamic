package com.dw.dynamic.repository;

import com.dw.dynamic.model.UserProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserProductRepository extends JpaRepository<UserProduct,Long> {
   // List<UserProduct> findByProductIdLike(String productName);
}
