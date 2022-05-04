package com.laptrinhjava.LichHoc.payload.response;

import javax.validation.constraints.NotBlank;

public class HomeResponse {
    private Long totalCourse;
    private Long planing;
    private Long planed;
    private Long totalRoom;
    private Long room20;
    private Long room30;
    private Long room40;

    // Constructor


    public HomeResponse(Long totalCourse, Long planing, Long planed, Long totalRoom, Long room20, Long room30, Long room40) {
        this.totalCourse = totalCourse;
        this.planing = planing;
        this.planed = planed;
        this.totalRoom = totalRoom;
        this.room20 = room20;
        this.room30 = room30;
        this.room40 = room40;
    }

    // Getter and Setter
    public Long getTotalCourse() {
        return totalCourse;
    }

    public void setTotalCourse(Long totalCourse) {
        this.totalCourse = totalCourse;
    }

    public Long getPlaning() {
        return planing;
    }

    public void setPlaning(Long planing) {
        this.planing = planing;
    }

    public Long getPlaned() {
        return planed;
    }

    public void setPlaned(Long planed) {
        this.planed = planed;
    }

    public Long getTotalRoom() {
        return totalRoom;
    }

    public void setTotalRoom(Long totalRoom) {
        this.totalRoom = totalRoom;
    }

    public Long getRoom20() {
        return room20;
    }

    public void setRoom20(Long room20) {
        this.room20 = room20;
    }

    public Long getRoom30() {
        return room30;
    }

    public void setRoom30(Long room30) {
        this.room30 = room30;
    }

    public Long getRoom40() {
        return room40;
    }

    public void setRoom40(Long room40) {
        this.room40 = room40;
    }
}
