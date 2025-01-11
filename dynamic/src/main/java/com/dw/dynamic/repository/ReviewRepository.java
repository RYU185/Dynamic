package com.dw.dynamic.repository;

import com.dw.dynamic.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review,Long> {
}
