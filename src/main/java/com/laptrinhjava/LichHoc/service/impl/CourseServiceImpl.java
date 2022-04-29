package com.laptrinhjava.LichHoc.service.impl;

import com.laptrinhjava.LichHoc.entity.Course;
import com.laptrinhjava.LichHoc.repository.CourseRepository;
import com.laptrinhjava.LichHoc.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseRepository courseRepository;

    @Override
    public Course save(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public Course findCourseById(Long id) {
        return courseRepository.findCourseById(id);
    }

    @Override
    public Course findCourseByRoomId(Long roomid) {
        return courseRepository.findCourseByRoomid(roomid);
    }

    @Override
    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    @Override
    public Optional<Course> findById(Long id) {
        return courseRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        courseRepository.deleteById(id);
    }
}
