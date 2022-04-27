package com.laptrinhjava.LichHoc.entity;

import javax.persistence.*;
import java.util.Date;

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

    @Column(name = "course_expire")
    private Long courseExpire;

    @Column(name = "is_can_start")
    private Boolean isCanStart;

    @Column(name = "roomid")
    private Long roomid;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createddate")
    private Date createdDate;

    @PrePersist
    private void onCreated() {
        createdDate = new Date();
    }

//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(name = "course_room",
//            joinColumns = @JoinColumn(name = "courseid"),
//            inverseJoinColumns = @JoinColumn(name = "roomid"))
//    private Set<Room> rooms = new HashSet<>();


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

    public Long getCourseExpire() {
        return courseExpire;
    }

    public void setCourseExpire(Long courseExpire) {
        this.courseExpire = courseExpire;
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

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getCreatedDate() {
        return createdDate;
    }
}
