package com.dw.dynamic.repository;

import com.dw.dynamic.model.PurchaseHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseHistoryRepository extends JpaRepository<PurchaseHistory,Long> {

    List<PurchaseHistory> findByProductNameLike(String productName);
}
