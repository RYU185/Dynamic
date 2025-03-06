package com.dw.dynamic.repository;

import com.dw.dynamic.model.Cart;
import com.dw.dynamic.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    @Query("select c from Cart c where c.user=:user and isActive=true")
    List<Cart> findByUser(User user);


    List<Cart> findByUserUserName(String userName);

    @Query("select c from Cart c where c.product.id=:id and c.user=:user")
    List<Cart> findByProductId(String id,User user);

    @Query("select c from Cart c where c.isActive=true")
    List<Cart> findByIsActive();

   @Query("select c from Cart c where c.product.category.name like %:categoryName% and c.user=:user")
    List<Cart> findByProductCategoryLike(String categoryName,User user);


}
