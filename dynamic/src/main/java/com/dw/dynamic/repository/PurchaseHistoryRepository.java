package com.dw.dynamic.repository;

import com.dw.dynamic.model.PurchaseHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseHistoryRepository extends JpaRepository<PurchaseHistory,Long> {
}
