package com.dw.dynamic.repository;

import com.dw.dynamic.model.Point;
import com.dw.dynamic.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PointRepository extends JpaRepository<Point,Long> {

    @Query("select p from Point p where p.purchaseHistory.user =:user")
    public List<Point> findByUser(User user);
}

