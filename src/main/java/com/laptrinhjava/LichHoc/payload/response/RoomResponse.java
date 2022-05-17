package com.laptrinhjava.LichHoc.payload.response;

import javax.validation.constraints.NotBlank;

public class RoomResponse {
    @NotBlank
    private Long id;

    @NotBlank
    private String roomName;

    @NotBlank
    private Long capacity;

    // constructor
    public RoomResponse(Long id, String roomName, Long capacity) {
        this.id = id;
        this.roomName = roomName;
        this.capacity = capacity;
    }

    // getter and setter

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Long getCapacity() {
        return capacity;
    }

    public void setCapacity(Long capacity) {
        this.capacity = capacity;
    }
}
