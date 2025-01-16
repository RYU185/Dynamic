package com.dw.dynamic.repository;

import com.dw.dynamic.model.FormationData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FormationDataRepository extends JpaRepository<FormationData,Long> {
    public List<FormationData> findByTitle(String title);
}
