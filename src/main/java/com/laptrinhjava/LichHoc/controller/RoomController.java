package com.laptrinhjava.LichHoc.controller;

import com.laptrinhjava.LichHoc.entity.Course;
import com.laptrinhjava.LichHoc.entity.ResponseObject;
import com.laptrinhjava.LichHoc.entity.Room;
import com.laptrinhjava.LichHoc.payload.response.RoomResponse;
import com.laptrinhjava.LichHoc.service.CourseService;
import com.laptrinhjava.LichHoc.service.RoomService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/room")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @Autowired
    private CourseService courseService;

    // danh sách phòng học
    @GetMapping("")
    List<RoomResponse> findIdRoomnameCapacity() {
        List<Room> findAll = roomService.findAll();
        List<RoomResponse> rooms = new ArrayList<>();
        RoomResponse roomResponse;
        for (Room room : findAll) {
            roomResponse = new RoomResponse(room.getId(), room.getRoomName(), room.getCapacity());
            rooms.add(roomResponse);
        }
        return rooms;
    }

    // thêm phòng học
    @PostMapping("/insert")
    ResponseEntity<ResponseObject> insert(@NotNull @RequestBody Room room) {
        if (roomService.existsByRoomName(room.getRoomName())) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("failed", "Room Name already exists!", "")
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Add room successfully!", roomService.save(room))
        );
    }

    // xếp lịch học
    @GetMapping("/schedule")
    List<Room> getAll() {
        return sortSchedule(roomService.findAll());
    }

    // xóa phòng học
    @DeleteMapping("/{id}")
    ResponseEntity<ResponseObject> delete(@PathVariable Long id) {
        Room foundRoom = roomService.findRoomById(id);
        if (foundRoom == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Delete failed!", "")
            );
        } else {

            if (!foundRoom.getLichLe().isEmpty() || !foundRoom.getLichChan().isEmpty())
                return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                        new ResponseObject("failed", "Delete failed", "")
                );
            else
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("ok", "Delete successfully!", "")
                );
        }
    }

    public List<Room> sortSchedule(List<Room> findRooms) {
        List<Course> lichChan; // mảng tạm lưu khóa học có lịch chẵn
        List<Course> lichLe; // mảng tạm lưu khóa học có lịch lẽ
        List<Course> findCourses = courseService.sortCourseByBubbleSort(courseService.findAll());
        Queue<Course> courseQueue = new ArrayDeque<Course>(); // hàng đợi lưu khóa học

        for (Room room : findRooms) {
            courseQueue.addAll(findCourses); // thêm các khóa học vào hàng đợi
            for (Course course : courseQueue) {
                // khởi tạo danh sách tạm
                lichChan = new ArrayList<>();
                lichLe = new ArrayList<>();

                if (course.getIsScheduled().equals(false)) { // khóa học chưa khóa
                    courseQueue.remove(course);
                } else if ((room.getCapacity() - course.getAmount() >= 0L) // số học viên < chỗ ngồi
                        && (room.getCapacity() - course.getAmount() < 10L) // chọn phòng phù hợp
                        && course.getIsScheduled().equals(true)) { // khóa học đã khóa
                    if (course.getSchedule().equals("1")) {
                        if (!room.getLichChan().isEmpty()) // nếu phòng đã có khóa học thì bỏ qua
                            break;
                        else {
                            lichChan.add(course); // thêm khóa học trong hàng đợi vào lichChan
                            room.setLichChan(lichChan);
                            roomService.save(room);
                            courseQueue.remove(course);
                        }
                    } else if (course.getSchedule().equals("2")) {
                        if (!room.getLichLe().isEmpty())
                            break;
                        else {
                            lichLe.add(course);
                            room.setLichLe(lichLe);
                            roomService.save(room);
                            courseQueue.remove(course);
                        }
                    } else {
                        if (room.getLichLe().isEmpty()) {
                            lichLe.add(course);
                            room.setLichLe(lichLe);
                            roomService.save(room);
                        }
                        if (room.getLichChan().isEmpty()) {
                            lichChan.add(course);
                            room.setLichChan(lichChan);
                            roomService.save(room);
                        }
                        courseQueue.remove(course);
                    }
                }

            }
        }
        return findRooms;
    }
}
