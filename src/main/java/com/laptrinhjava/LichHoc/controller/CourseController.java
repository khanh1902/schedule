package com.laptrinhjava.LichHoc.controller;

import com.laptrinhjava.LichHoc.entity.Course;
import com.laptrinhjava.LichHoc.entity.ResponseObject;
import com.laptrinhjava.LichHoc.entity.Room;
import com.laptrinhjava.LichHoc.payload.request.CourseRequest;
import com.laptrinhjava.LichHoc.repository.CourseRepository;
import com.laptrinhjava.LichHoc.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("api/course")
@CrossOrigin(origins = "http://localhost:1212")
public class CourseController {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private RoomService roomService;

    @GetMapping("")
    List<Course> getAll(){
        return courseRepository.findAll();
    }

    @PostMapping("/insert")
    ResponseEntity<ResponseObject> insert(@RequestBody CourseRequest courseRequest){
        Course course = new Course();
        List<Long> strRooms = courseRequest.getRooms();
        Iterator<Long> iterator = null; // khai báo một Iterator
        Set<Room> rooms = new HashSet<>();

        iterator = strRooms.iterator();
        while (iterator.hasNext()) {
            Room setRoom = roomService.findById(iterator.next())
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            rooms.add(setRoom);
        }
        course.setCourseName(courseRequest.getCourseName());
        course.setSchedule(courseRequest.getSchedule());
        course.setAmount(courseRequest.getAmount());
        course.setRooms(rooms);

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Add course successfully!", courseRepository.save(course))
        );
    }
}
