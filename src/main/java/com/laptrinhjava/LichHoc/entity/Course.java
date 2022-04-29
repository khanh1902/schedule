package com.laptrinhjava.LichHoc.entity;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "course_name")
    private String courseName;

    @Column(name = "schedule")
    private String schedule;

    @Column(name = "amount")
    private Long amount;

    @Column(name = "duration")
    private Long duration;

    @Column(name = "is_scheduled")
    private Boolean isScheduled;

    @Column(name = "is_can_start")
    private Boolean isCanStart;

    @Column(name = "roomid")
    private Long roomid;


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

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Boolean getIsScheduled() {
        return isScheduled;
    }

    public void setIsScheduled(Boolean scheduled) {
        isScheduled = scheduled;
    }

    public Boolean getCanStart() {
        return isCanStart;
    }

    public void setCanStart(Boolean canStart) {
        isCanStart = canStart;
    }

    public Long getRoomid() {
        return roomid;
    }

    public void setRoomid(Long roomid) {
        this.roomid = roomid;
    }
}
