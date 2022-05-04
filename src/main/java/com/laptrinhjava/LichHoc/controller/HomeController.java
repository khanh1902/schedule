package com.laptrinhjava.LichHoc.controller;

import com.laptrinhjava.LichHoc.entity.Course;
import com.laptrinhjava.LichHoc.entity.ResponseObject;
import com.laptrinhjava.LichHoc.entity.Room;
import com.laptrinhjava.LichHoc.payload.response.HomeResponse;
import com.laptrinhjava.LichHoc.service.CourseService;
import com.laptrinhjava.LichHoc.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/home")
public class HomeController {
    @Autowired
    private CourseService courseService;

    @Autowired
    private RoomService roomService;

    @GetMapping("")
    ResponseEntity<ResponseObject> getHome(){
        Long totalCourse = 0L;
        Long planing = 0L;
        Long planed = 0L;
        Long totalRoom = 0L;
        Long room20 = 0L;
        Long room30 = 0L;
        Long room40 = 0L;
        List<Course> findCourse = courseService.findAll();
        for (Course course : findCourse){
                totalCourse ++;
            if (course.getIsScheduled()==true)
                planed ++;
            else
                planing ++;
        }

        List<Room> findRoom = roomService.findAll();
        for(Room room : findRoom){
            totalRoom ++;
            if(room.getCapacity()==20)
                room20++;
            else if (room.getCapacity()==30)
                room30++;
            else
                room40++;

        }

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Get Successfully!", new HomeResponse(totalCourse, planing,
                        planed, totalRoom, room20, room30, room40))
        );
    }
}
