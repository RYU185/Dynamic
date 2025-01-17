package com.dw.dynamic.repository;

import com.dw.dynamic.model.Cart;
import com.dw.dynamic.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByUser(User user);
}
