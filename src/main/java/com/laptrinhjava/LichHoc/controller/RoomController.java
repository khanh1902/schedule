package com.laptrinhjava.LichHoc.controller;

import com.laptrinhjava.LichHoc.entity.ResponseObject;
import com.laptrinhjava.LichHoc.entity.Room;
import com.laptrinhjava.LichHoc.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/room")
@CrossOrigin(origins = "http://localhost:1212")
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
                    new ResponseObject("failed", "Room Name available!", "")
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Add room successfully!", roomService.save(room))
        );
    }
}
