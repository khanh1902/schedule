package com.laptrinhjava.LichHoc.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "room")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "roomname")
    private String roomName;

    @Column(name = "capacity")
    private Long capacity;

//    @ManyToMany(mappedBy = "rooms", fetch = FetchType.LAZY)
//    private List<Course> courses = new ArrayList<>();

    // Constructor
    public Room() {
    }

    // Getter and Setter
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

//    public List<Course> getCourses() {
//        return courses;
//    }

//    public void setCourses(List<Course> courses) {
//        this.courses = courses;
//    }
}
