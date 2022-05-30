package com.laptrinhjava.LichHoc.service.impl;

import com.laptrinhjava.LichHoc.entity.Course;
import com.laptrinhjava.LichHoc.repository.CourseRepository;
import com.laptrinhjava.LichHoc.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
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
    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    @Override
    public List<Course> findCourseByDuration(Long duration) {
        return courseRepository.findCourseByDuration(duration);
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
                    else {
                        course.setAmount(newCourse.getAmount());

                        // set can start
                        if (newCourse.getAmount() < 15L) {
                            course.setIsScheduled(false);
                        } else {
                            course.setIsScheduled(true);
                        }
                    }

                    // set schedule (lichhoc)
                    if (newCourse.getSchedule() == null)
                        course.setSchedule(foundCourse.getSchedule());
                    else
                        course.setSchedule(newCourse.getSchedule());

                    // set duration
                    if (newCourse.getDuration() == null)
                        course.setDuration(foundCourse.getDuration());
                    else
                        course.setDuration(newCourse.getDuration());

                    return courseRepository.save(course);
                }).orElseGet(() -> {
            newCourse.setId(newCourse.getId());
            return courseRepository.save(newCourse);
        });
        return updateCourse;
    }

    @Override
    public List<Course> sortCourseByBubbleSort(List<Course> findCourses) {
        for (int i = 0; i < findCourses.size() - 1; i++) {
            for (int j = findCourses.size() - 1; j > i; j--) {
                if (findCourses.get(j).getDuration().compareTo(findCourses.get(j - 1).getDuration()) < 0L) {
                    Course temp = findCourses.get(j);
                    findCourses.set(j, findCourses.get(j - 1));
                    findCourses.set(j - 1, temp);
                } else if (findCourses.get(j).getDuration().compareTo(findCourses.get(j - 1).getDuration()) == 0L
                        && findCourses.get(j).getAmount().compareTo(findCourses.get(j - 1).getAmount()) > 0L) {
                    Course temp = findCourses.get(j);
                    findCourses.set(j, findCourses.get(j - 1));
                    findCourses.set(j - 1, temp);
                }
            }
        }
        return findCourses;
    }

    @Override
    public Course updateCreatedDate(Long id, Date createddate, Long duration) {
        Course updateCourse = courseRepository.findById(id).map(
                course -> {
                    // set createddate
                    Date oldDate = createddate; // lấy thời gian từ khóa học trước
                    Date newDate = new Date(oldDate.getTime() + ((1000 * 60 * 60 * 24) * 7 * duration));
                    course.setCreatedDate(newDate);
                    return courseRepository.save(course);
                }
        ).orElseThrow();
        return updateCourse;
    }

    @Override
    public List<Course> sortCourseByGreedyAlgorithm(List<Course> courses, int typeSchedule) {
        // Sort all course according to decreasing order of amount
        sortCourseByBubbleSort(courses);

        // To keep track of free time slots
        boolean[] result = new boolean[typeSchedule];

        // To store result (Sequence of courses)
        Long[] resultWithCourseId = new Long[typeSchedule];

        // Iterate through all given schedule
        for (int i = 0; i < courses.size(); i++) {
            // Find a free schedule for this course (Note that we
            // start from the last possible slot)
            for (int j = Math.toIntExact(Math.min(typeSchedule - 1, courses.get(i).getDuration() - 1)); j >= 0; j--) {
                // Free slot found
                if (result[j] == false) {
                    result[j] = true;
                    resultWithCourseId[j] = courses.get(i).getId();
                    break;
                }
            }
        }

        // remove all courses to add the sorted courses
        courses.clear();
        for (int i = 0; i < resultWithCourseId.length; i++) {
            courses.add(i, findCourseById(resultWithCourseId[i]));
        }
        // return list course id
        return courses;
    }
}
