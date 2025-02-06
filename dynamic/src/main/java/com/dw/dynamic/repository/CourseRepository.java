package com.dw.dynamic.repository;

import com.dw.dynamic.model.Course;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course,String> {
    List<Course> findByTitle(String title);

    List<Course> findByTitleLike(String title);
//
//    @Transactional // 혹시 몰라서 추가했습니다
//    void deleteByTitle(String title);

    @Query("select c from Course c join Product p on c.id=p.id where p.isActive=true")
    List<Course> findAllWhereProductIsActiveIsTrue();

    @Query("select c from Course c join Product p on c.id=p.id where p.isActive=true and c.id=:id")
    Course findByIdWhereProductIsActiveIsTrue(String id);

    @Query("select c from Course c join Product p on c.id=p.id where p.isActive=true and c.title like %:title%")
    List<Course> findByTitleWhereProductIsActiveIsTrue(String title);

}
