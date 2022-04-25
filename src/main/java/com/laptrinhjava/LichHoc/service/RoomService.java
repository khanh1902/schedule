package com.laptrinhjava.LichHoc.service;

import com.laptrinhjava.LichHoc.entity.Room;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface RoomService {
    List<Room> findAll();
    Room save(Room room);
    Boolean existsByRoomName(String roomName);
    Room findRoomById(Long id);
    Optional<Room> findById(Long id);

}
