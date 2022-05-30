package com.laptrinhjava.LichHoc.service;

import com.laptrinhjava.LichHoc.entity.Course;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public interface CourseService {
    Course save(Course course);
    Course findCourseById(Long id);
    List<Course> findAll();
    List<Course> findCourseByDuration(Long duration);
    Optional<Course> findById(Long id);
    void delete(Long id);
    Course update(Course newCourse, Long id);
    List<Course> sortCourseByBubbleSort(List<Course> findCourses);
    Course updateCreatedDate(Long id, Date createddate, Long duration);
    List<Course> sortCourseByGreedyAlgorithm(List<Course> courses, int typeRoom);
}
