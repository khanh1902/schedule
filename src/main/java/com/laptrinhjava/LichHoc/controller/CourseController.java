package com.laptrinhjava.LichHoc.controller;

import com.laptrinhjava.LichHoc.entity.Course;
import com.laptrinhjava.LichHoc.entity.ResponseObject;
import com.laptrinhjava.LichHoc.entity.Room;
import com.laptrinhjava.LichHoc.payload.request.CourseRequest;
import com.laptrinhjava.LichHoc.repository.CourseRepository;
import com.laptrinhjava.LichHoc.service.CourseService;
import com.laptrinhjava.LichHoc.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("api/course")
//@CrossOrigin(origins = "http://localhost:3000",  maxAge = 3600)
public class CourseController {
    @Autowired
    private CourseService courseService;

    @Autowired
    private RoomService roomService;

    @GetMapping("")
    List<Course> getAll() {
        return courseService.findAll();
    }

    @PostMapping("/insert")
    ResponseEntity<ResponseObject> insert(@RequestBody Course course) {
        Room foundRoom = roomService.findRoomById(course.getRoomid());
        if (foundRoom.getCapacity() < course.getAmount()) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("failed", "Amount must be less than " + foundRoom.getCapacity() + "!", "")
            );
        }
        if (course.getAmount() < 15L) {
            course.setCanStart(false);
        } else {
            course.setCanStart(true);
        }
        course.setIsScheduled(false);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Add course successfully!", courseService.save(course))
        );
    }

    // Update course
    @PutMapping("/{id}")
    ResponseEntity<ResponseObject> update(@Valid @RequestBody Course newCourse, @PathVariable Long id) {
        Course foundCourse = courseService.findCourseById(id); // Lấy thông tin cũ
        Course updateCourse = courseService.findById(id).map(
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

                    return courseService.save(course);
                }).orElseGet(() -> {
            newCourse.setId(newCourse.getId());
            return courseService.save(newCourse);
        });

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Update course successfully!", updateCourse)
        );
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ResponseObject> delete(@PathVariable Long id) {
        Course foundCourse = courseService.findCourseById(id);
        if (foundCourse == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Delete failed!", "")
            );
        } else {
            if (foundCourse.getCanStart() == true) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject("failed", "Delete failed. Class locked!", "")
                );
            } else {
                courseService.delete(id);
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("ok", "Delete successfully!", "")
                );
            }
        }
    }
}