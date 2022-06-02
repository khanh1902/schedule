package com.laptrinhjava.LichHoc.controller;

import com.laptrinhjava.LichHoc.entity.Course;
import com.laptrinhjava.LichHoc.entity.ResponseObject;
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
public class CourseController {
    @Autowired
    private CourseService courseService;

    @Autowired
    private RoomService roomService;

    @GetMapping("")
    List<Course> getAll() {
        return courseService.sortCourseByBubbleSort(courseService.findAll());
    }

    @PostMapping("/insert")
    ResponseEntity<ResponseObject> insert(@RequestBody Course course) {
        if (course.getAmount() < 15L) {
            course.setIsScheduled(false);
        } else {
            course.setIsScheduled(true);
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Add course successfully!", courseService.save(course))
        );
    }

    // Update course
    @PutMapping("/{id}")
    ResponseEntity<ResponseObject> update(@Valid @RequestBody Course newCourse, @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Update course successfully!", courseService.update(newCourse, id))
        );
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ResponseObject> delete(@PathVariable Long id) {
        Course foundCourse = courseService.findCourseById(id);
        if (foundCourse == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Delete failed. Course not exists!", "")
            );
        } else {
            courseService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Delete successfully!", "")
            );
        }
    }
}