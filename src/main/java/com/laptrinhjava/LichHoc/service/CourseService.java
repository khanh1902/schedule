package com.laptrinhjava.LichHoc.service;

import com.laptrinhjava.LichHoc.entity.Course;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CourseService {
    Course save(Course course);
    Course findCourseById(Long id);
    List<Course> findAll();
    Optional<Course> findById(Long id);
    void delete(Long id);
}
