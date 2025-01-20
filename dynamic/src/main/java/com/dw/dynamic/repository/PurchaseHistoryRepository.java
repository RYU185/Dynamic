package com.dw.dynamic.repository;

import com.dw.dynamic.model.PurchaseHistory;
import com.dw.dynamic.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PurchaseHistoryRepository extends JpaRepository<PurchaseHistory,Long> {

    PurchaseHistory findByProductId(String productId);

    @Query("select c from Course c where c.title like %:title%")
    List<PurchaseHistory> findCourseByTitleLike(String title);

    @Query("select ps from PayrollSubscription ps where ps.title like %:title%")
    List<PurchaseHistory> findPayrollSubscriptionByTitleLike(String title);

    List<PurchaseHistory> findByUser(User currentUser);



}