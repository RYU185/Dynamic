package com.dw.dynamic.repository;

import com.dw.dynamic.model.Guide;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuideRepository extends JpaRepository<Guide,Long> {
}
