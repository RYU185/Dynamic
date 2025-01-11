package com.dw.dynamic.repository;

import com.dw.dynamic.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course,String> {
}
