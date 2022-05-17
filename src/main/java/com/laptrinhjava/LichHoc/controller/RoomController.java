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
    @GetMapping("schedule")
    List<Room> getAll() {
        return roomService.sortSchedule(roomService.findAll());
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
}
