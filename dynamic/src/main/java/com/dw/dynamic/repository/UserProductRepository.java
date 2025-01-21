package com.dw.dynamic.repository;

import com.dw.dynamic.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserProductRepository extends JpaRepository<UserProduct,Long> {
    List<UserProduct> findByUser(User currentUser);

     UserProduct findByProductId( String productId);

    @Query("select up from UserProduct up " +
            "join up.product p " +
            "left join Course c on p.id = c.id " +
            "left join PayrollSubscription ps on p.id = ps.id " +
            "where up.user.userName = :currentUser " +
            "and (c.title like %:productName% or ps.title like %:productName%)")
    List<UserProduct> findByProductNameLike(String currentUser, String productName);




}
