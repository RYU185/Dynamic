package com.dw.dynamic.repository;

import com.dw.dynamic.model.Course;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course,String> {
    List<Course> findByTitle(String title);

    List<Course> findByTitleLike(String title);

    @Transactional // 혹시 몰라서 추가했습니다
    void deleteByTitle(String title);
}
