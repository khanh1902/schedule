package com.laptrinhjava.LichHoc.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.EqualsAndHashCode;
import lombok.ToString;

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

//    @Column(name = "duration")
//    private Long duration;

    @Column(name = "is_scheduled")
    private Boolean isScheduled;

    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    @Column (name = "startdate")
    private String startDate;

    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    @Column (name = "enddate")
    private String endDate;

//    @Temporal(TemporalType.TIMESTAMP)
//    @Column(name = "createddate", nullable = false)
//    private Date createdDate;
//
//    @PrePersist
//    private void onCreated() {
//        createdDate = new Date();
//    } // init system time

    @ManyToOne
    @JoinColumn(name = "roomid")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Room room;

    // Constructor
    public Course() {
    }

    public Course(Long id, String courseName, String schedule, Long amount, Boolean isScheduled, String startDate, String endDate) {
        this.id = id;
        this.courseName = courseName;
        this.schedule = schedule;
        this.amount = amount;
        this.isScheduled = isScheduled;
        this.startDate = startDate;
        this.endDate = endDate;
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

//    public Long getDuration() {
//        return duration;
//    }
//
//    public void setDuration(Long duration) {
//        this.duration = duration;
//    }

    public Boolean getIsScheduled() {
        return isScheduled;
    }

    public void setIsScheduled(Boolean scheduled) {
        isScheduled = scheduled;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
