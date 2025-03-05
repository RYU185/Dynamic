package com.dw.dynamic.repository;

import com.dw.dynamic.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Objects;

public interface PurchaseHistoryRepository extends JpaRepository<PurchaseHistory,Long> {

    PurchaseHistory findByProductId(String id);


    List<PurchaseHistory> findByUser(User currentUser);

    @Query("select up from PurchaseHistory up " +
            "join up.product p " +
            "left join Course c on p.id = c.id " +
            "left join PayrollSubscription ps on p.id = ps.id " +
            "where up.user.userName = :currentUser " +
            "and (c.title like %:productName% OR ps.title like %:productName%)")
    List<PurchaseHistory> findByProductNameLike(String productName, String currentUser);

    List<PurchaseHistory> findByUser_UserName(String userName);

    @Query("select ph from PurchaseHistory ph " +
            "join ph.product p " +
            "where ph.user.userName = :userName " +
            "and p.category.name = '정기 구독권' " +
            "and type(p) = com.dw.dynamic.model.PayrollSubscription " + // 상속된 클래스 필터링
            "order by p.expireDate desc")
    PurchaseHistory getPurchaseHistoryOfPayrollsubscriptionByUserName(String userName);


}