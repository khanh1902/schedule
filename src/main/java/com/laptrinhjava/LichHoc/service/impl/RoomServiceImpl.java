package com.laptrinhjava.LichHoc.service.impl;

import com.laptrinhjava.LichHoc.entity.Course;
import com.laptrinhjava.LichHoc.entity.Room;
import com.laptrinhjava.LichHoc.repository.RoomRepository;
import com.laptrinhjava.LichHoc.service.CourseService;
import com.laptrinhjava.LichHoc.service.RoomService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RoomServiceImpl implements RoomService {
    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private CourseService courseService;

    @Override
    public List<Room> findAll() {
        return roomRepository.findAll();
    }

    @Override
    public Room save(Room room) {
        return roomRepository.save(room);
    }

    @Override
    public Boolean existsByRoomName(String roomName) {
        return roomRepository.existsByRoomName(roomName);
    }

    @Override
    public Room findRoomById(Long id) {
        return roomRepository.findRoomById(id);
    }

    @Override
    public Optional<Room> findById(Long id) {
        return roomRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        roomRepository.deleteById(id);
    }


    @Override
    public List<Room> sortRoom(List<Room> roomList) {
        for (int i = 0; i < roomList.size() - 1; i++) {
            for (int j = roomList.size() - 1; j > i; j--) {
                if (roomList.get(j).getCapacity().compareTo(roomList.get(j - 1).getCapacity()) > 0L) {
                    Room temp = roomList.get(j);
                    roomList.set(j, roomList.get(j - 1));
                    roomList.set(j - 1, temp);
                }
            }
        }
        return roomList;
    }

    // thuật toán sắp xếp lịch học
    // lưu các khóa học sau khi tham lam vào hàng đợi
    @Override
    public List<Room> sortSchedule(List<Room> findRooms) {
        sortRoom(findRooms); // sort esc room by capacity
        List<Course> courses = courseService.findAll();
        Queue<Course> courseQueue = new ArrayDeque<>();
        while (!courses.isEmpty()) {
            List<Course> findCourses = courseService.sortCourseByGreedyAlgorithm(courses);
            courseQueue.addAll(findCourses);
            courseService.deleteCoursesById(courses, findCourses);
        }
        for (Course findCourse : courseQueue) {
            List<Course> courseTemp = new ArrayList<>();
            courseTemp.add(findCourse);
            for (Room findRoom : findRooms) {
                if (findCourse.getIsScheduled().equals(false)) { // course is not locked
                    break;
                } else if (findCourse.getIsScheduled().equals(true)
                        && (findRoom.getCapacity() - findCourse.getAmount() >= 0L) // amount < capacity
                        && (findRoom.getCapacity() - findCourse.getAmount() < 10L)) { // capacity - amount < 10
                    if (findCourse.getSchedule().equals("1")) { // schedule = 1 => lichChan
                        // if room null, add the course
                        if (findRoom.getLichChan().isEmpty()) {
                            findRoom.setLichChan(courseTemp);
                            roomRepository.save(findRoom);
                            break;
                        } else continue;
                    } else if (findCourse.getSchedule().equals("2")) { // schedule = 2 => lichLe
                        if (findRoom.getLichLe().isEmpty()) {
                            findRoom.setLichLe(courseTemp);
                            roomRepository.save(findRoom);
                            break;
                        } else continue;
                    } else { // course has schedule = 3. if room has lichChan or lichLe null, add the course
                        if (findRoom.getLichLe().isEmpty()) {
                            findRoom.setLichLe(courseTemp);
                            roomRepository.save(findRoom);
                        }
                        if (findRoom.getLichChan().isEmpty()) {
                            findRoom.setLichChan(courseTemp);
                            roomRepository.save(findRoom);
                        }
                        break;
                    }
                }
            }
        }
        return findRooms;
    }
}