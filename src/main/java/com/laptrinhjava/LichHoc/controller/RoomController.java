package com.laptrinhjava.LichHoc.controller;

import com.laptrinhjava.LichHoc.entity.Course;
import com.laptrinhjava.LichHoc.entity.ResponseObject;
import com.laptrinhjava.LichHoc.entity.Room;
import com.laptrinhjava.LichHoc.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/room")
@CrossOrigin(origins = "http://localhost:3000")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @GetMapping("")
    List<Room> getAll(){
        return roomService.findAll();
    }

    @PostMapping("/insert")
    ResponseEntity<ResponseObject> insert(@RequestBody Room room){
        if(roomService.existsByRoomName(room.getRoomName())){
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("failed", "Room Name already exists!", "")
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Add room successfully!", roomService.save(room))
        );
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ResponseObject> delete(@PathVariable Long id){
        Optional<Room> foundRoom = roomService.findById(id);
        if(!foundRoom.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Delete failed!", "")
            );
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Delete successfully!", "")
            );
        }
    }
}
