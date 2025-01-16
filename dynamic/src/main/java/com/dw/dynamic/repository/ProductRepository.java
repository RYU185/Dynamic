package com.dw.dynamic.repository;

import com.dw.dynamic.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,String> {
    List<Product> findByTitleLike(String title);
}
