package com.dw.dynamic.repository;

import com.dw.dynamic.model.Guide;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GuideRepository extends JpaRepository<Guide,Long> {
    public List<Guide> findByTitle(String title);
}
