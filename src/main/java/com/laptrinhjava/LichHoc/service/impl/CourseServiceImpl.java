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

    @Override
    public Course update(Course newCourse, Long id) {
        Course foundCourse = courseRepository.findCourseById(id); // Lấy thông tin cũ
        Course updateCourse = courseRepository.findById(id).map(
                course -> {
                    // set course name
                    if (newCourse.getCourseName() == null)
                        course.setCourseName(foundCourse.getCourseName());
                    else
                        course.setCourseName(newCourse.getCourseName());

                    // set amount
                    if (newCourse.getAmount() == null)
                        course.setAmount(foundCourse.getAmount());
                    else{
                        course.setAmount(newCourse.getAmount());

                        // set can start
                        if (newCourse.getAmount() < 15L) {
                            course.setCanStart(false);
                        } else {
                            course.setCanStart(true);
                        }
                    }

                    // set schedule
                    if (newCourse.getSchedule() == null)
                        course.setSchedule(foundCourse.getSchedule());
                    else
                        course.setSchedule(newCourse.getSchedule());

                    // set duration
                    if(newCourse.getDuration() == null)
                        course.setDuration(foundCourse.getDuration());
                    else
                        course.setDuration(newCourse.getDuration());

                    // set roomid
                    if (newCourse.getRoomid() == null)
                        course.setRoomid(foundCourse.getRoomid());
                    else
                        course.setRoomid(newCourse.getRoomid());

                    // set is scheduled
                    if(newCourse.getIsScheduled()==null)
                        course.setIsScheduled(foundCourse.getIsScheduled());
                    else
                        course.setIsScheduled(newCourse.getIsScheduled());

                    return courseRepository.save(course);
                }).orElseGet(() -> {
            newCourse.setId(newCourse.getId());
            return courseRepository.save(newCourse);
        });
        return updateCourse;
    }
}
