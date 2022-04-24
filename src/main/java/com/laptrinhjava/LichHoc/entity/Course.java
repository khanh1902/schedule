package com.laptrinhjava.LichHoc.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "coursename")
    private String courseName;

    @Column(name = "schedule")
    private String schedule;

    @Column(name = "amount")
    private Long amount;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createddate", nullable = false)
    private Date createdDate;

    @PrePersist
    private void onCreated() {
        createdDate = new Date();
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "course_room",
            joinColumns = @JoinColumn(name = "courseid"),
            inverseJoinColumns = @JoinColumn(name = "roomid"))
    private Set<Room> rooms = new HashSet<>();

    // Constructor
    public Course() {
    }

    // Getter and Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Set<Room> getRooms() {
        return rooms;
    }

    public void setRooms(Set<Room> rooms) {
        this.rooms = rooms;
    }

    public Date getCreatedDate() {
        return createdDate;
    }
}
