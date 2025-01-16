package com.dw.dynamic.service;

import com.dw.dynamic.model.Course;
import com.dw.dynamic.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    @Autowired
    CourseRepository courseRepository;

    public List<Course> getAllCourses(){
        return courseRepository.findAll().stream().toList();
    }


    public List<Course> getCoursesByTitle(String title){
        return courseRepository.findByTitleLike("%"+title+"%").stream().toList();
    }


}
