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
@CrossOrigin(origins = "http://localhost:3000",  maxAge = 3600)
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

    // Update course
    @PutMapping("/{id}")
    ResponseEntity<ResponseObject> update(@Valid @RequestBody Course newCourse, @PathVariable Long id) {
        Course foundCourse = courseService.findCourseById(id);
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
                    else
                        course.setAmount(newCourse.getAmount());

                    // set schedule
                    if (newCourse.getSchedule()== null)
                        course.setSchedule(foundCourse.getSchedule());
                    else
                        course.setSchedule(newCourse.getSchedule());

                    // set roomid
                    if(newCourse.getRoomid() == null)
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
    ResponseEntity<ResponseObject> delete(@PathVariable Long id){
        Optional<Course> foundCourse = courseService.findById(id);
        if(!foundCourse.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Delete failed!", "")
            );
        }
        else {
            courseService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Delete successfully!", "")
            );
        }
    }
}