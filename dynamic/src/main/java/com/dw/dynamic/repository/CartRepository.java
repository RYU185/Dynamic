package com.dw.dynamic.repository;

import com.dw.dynamic.model.Cart;
import com.dw.dynamic.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByUser(User user);

    List<Cart> findByProductId(String id);

    @Query("select c from Cart c where c.isActive=true")
    List<Cart> findByIsActive();
}
