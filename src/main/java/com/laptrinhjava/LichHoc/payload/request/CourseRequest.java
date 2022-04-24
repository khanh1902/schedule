package com.laptrinhjava.LichHoc.payload.request;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class CourseRequest {
    @NotBlank
    private String courseName;

    @NotBlank
    private String schedule;

    @NotBlank
    private Long amount;

    private List<Long> rooms;

    // Getter and Setter
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

    public List<Long> getRooms() {
        return rooms;
    }

    public void setRooms(List<Long> rooms) {
        this.rooms = rooms;
    }
}
