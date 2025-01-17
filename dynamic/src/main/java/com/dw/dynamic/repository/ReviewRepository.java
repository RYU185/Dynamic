package com.dw.dynamic.repository;

import com.dw.dynamic.DTO.ReviewDTO;
import com.dw.dynamic.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,Long> {
    public List<ReviewDTO> findByProductId(String productId);
}
