package com.dw.dynamic.repository;

import com.dw.dynamic.model.User;
import com.dw.dynamic.model.UserProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserProductRepository extends JpaRepository<UserProduct,Long> {
    List<UserProduct> findByUser(User currentUser);

    @Query("select up from UserProduct up " +
            "where up.user = :currentUser " +
            "and up.product.id = :productId")
    List<UserProduct> findByProductId(User currentUser, String productId);

    @Query("select up from UserProduct up " +
            "join up.product p " +
            "left join Course c on p.id = c.id " +
            "left join PayrollSubscription ps on p.id = ps.id " +
            "where up.user = :currentUser " +
            "and (c.title like %:productName% or ps.title like %:productName%)")
    List<UserProduct> findByProductNameLike(User currentUser, String productName);
}
