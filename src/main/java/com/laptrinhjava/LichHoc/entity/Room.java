package com.laptrinhjava.LichHoc.entity;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "room")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "roomname")
    private String roomName;

    @Column(name = "capacity")
    private Long capacity;

    // quan he 1-n: 1 phong co nhieu khoa hoc
    // luu khoa hoc co schudule = 1
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<Course> lichChan;

    // quan he 1-n: 1 phong co nhieu khoa hoc
    // luu khoa hoc co schudule = 2
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude // không sử dụng trường này trong equals và hashcode
    @ToString.Exclude // Khoonhg sử dụng trong toString()
    private List<Course> lichLe;

    // Constructor
    public Room() {
    }

    public Room(Long id, String roomName, Long capacity) {
        this.id = id;
        this.roomName = roomName;
        this.capacity = capacity;
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

    public Collection<Course> getLichChan() {
        return lichChan;
    }

    public void setLichChan(List<Course> lichChan) {
        this.lichChan = lichChan;
    }

    public List<Course> getLichLe() {
        return lichLe;
    }

    public void setLichLe(List<Course> lichLe) {
        this.lichLe = lichLe;
    }
}
