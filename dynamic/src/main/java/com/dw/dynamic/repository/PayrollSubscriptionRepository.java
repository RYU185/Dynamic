package com.dw.dynamic.repository;

import com.dw.dynamic.model.PayrollSubscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PayrollSubscriptionRepository extends JpaRepository<PayrollSubscription,String> {

    List<PayrollSubscription> findByTitleLike(String title);
}
