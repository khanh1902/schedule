package com.laptrinhjava.LichHoc.service.impl;

import com.laptrinhjava.LichHoc.entity.Course;
import com.laptrinhjava.LichHoc.repository.CourseRepository;
import com.laptrinhjava.LichHoc.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

                    // set startDate
                    if (newCourse.getStartDate() == null)
                        course.setStartDate(foundCourse.getStartDate());
                    else
                        course.setStartDate(newCourse.getStartDate());

                    // set endDate
                    if (newCourse.getEndDate() == null)
                        course.setEndDate(foundCourse.getEndDate());
                    else
                        course.setEndDate(foundCourse.getEndDate());

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
                if (findCourses.get(j).getEndDate().compareTo(findCourses.get(j - 1).getEndDate()) < 0L) {
                    Course temp = findCourses.get(j);
                    findCourses.set(j, findCourses.get(j - 1));
                    findCourses.set(j - 1, temp);
                }
            }
        }
        return findCourses;
    }

    @Override
    public List<Course> sortCourseByGreedyAlgorithm(List<Course> courses) {
        sortCourseByBubbleSort(courses);                                              // n^2
        List<Course> result = new ArrayList<>();                                      // 1
        Course end = courses.get(0);                                                  // 1
        result.add(end);                                                              // 1
        for(int i = 0; i< courses.size(); i++){                                       // n
                if(courses.get(i).getEndDate().equals(end.getEndDate())){             // 1
                    continue;
                }
                if(courses.get(i).getStartDate().compareTo(end.getEndDate()) >= 0){   // 1
                    end = courses.get(i);
                    result.add(courses.get(i));
                }
        }
        return result;                                                                // 1
    }

    @Override
    public List<Course> deleteCoursesById(List<Course> findCourses, List<Course> coursesToDelete) {
        for (int i = 0; i < findCourses.size(); i++) {
            for (int j = 0; j < coursesToDelete.size(); j++) {
                if (findCourses.get(i).equals(coursesToDelete.get(j))) {
                    findCourses.remove(findCourses.get(i));
                }
            }
        }
        return findCourses;
    }
}
