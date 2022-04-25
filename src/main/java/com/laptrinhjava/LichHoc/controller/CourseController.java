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
@CrossOrigin(origins = "http://localhost:1212")
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
//        Course course = new Course();
//        List<Long> strRooms = courseRequest.getRooms();
//        Iterator<Long> iterator = null; // khai báo một Iterator
//        Set<Room> rooms = new HashSet<>();
//
//        iterator = strRooms.iterator();
//        while (iterator.hasNext()) {
//            Room setRoom = roomService.findById(iterator.next())
//                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//            rooms.add(setRoom);
//        }
//        course.setCourseName(courseRequest.getCourseName());
//        course.setSchedule(courseRequest.getSchedule());
//        course.setAmount(courseRequest.getAmount());
//        course.setRooms(rooms);
        Room foundRoom = roomService.findRoomById(course.getRoomid());
        if (foundRoom.getCapacity() < course.getAmount()) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("failed", "Amount must be less than " + foundRoom.getCapacity() + "!", "")
            );
        }

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Add course successfully!", courseService.save(course))
        );
    }

    @PatchMapping("/{id}")
    ResponseEntity<ResponseObject> update(@Valid @RequestBody Course newCourse, @PathVariable Long id) {
        Course updateCourse = courseService.findById(id).map(
                course -> {
                    course.setCourseName(newCourse.getCourseName());
                    course.setSchedule(newCourse.getSchedule());
                    course.setAmount(newCourse.getAmount());
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
}
