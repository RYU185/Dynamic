package com.dw.dynamic.repository;

import com.dw.dynamic.model.Product;
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

    @Query("select ph.product.id , c.title, ps.title from PurchaseHistory ph join Course c on ph.product.id=c.id join PayrollSubscription ps on ph" +
            ".product.id = ps.id where c.title =:productName or ps.title =:productName")
    List<PurchaseHistory> findByProductNameLike(String productName);



}