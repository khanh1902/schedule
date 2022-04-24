package com.laptrinhjava.LichHoc.repository;

import com.laptrinhjava.LichHoc.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    Boolean existsByRoomName(String roomName);
}
