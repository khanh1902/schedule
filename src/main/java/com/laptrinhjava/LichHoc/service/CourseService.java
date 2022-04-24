package com.laptrinhjava.LichHoc.service;

import com.laptrinhjava.LichHoc.entity.Course;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CourseService {
    Course save(Course course);
    List<Course> findAll();
}
