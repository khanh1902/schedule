package com.laptrinhjava.LichHoc.repository;

import com.laptrinhjava.LichHoc.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    Course findCourseById(Long id);
    List<Course> findCourseByDuration(Long duration);
}
