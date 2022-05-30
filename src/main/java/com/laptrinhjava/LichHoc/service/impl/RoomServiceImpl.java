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
    public List<Course> deleteCoursesById(List<Course> findCourses, List<Course> coursesToDelete) {
        for (int i = 0; i < findCourses.size(); i++) {
            for (int j = 0; j < coursesToDelete.size(); j++) {
                if (findCourses.get(i).equals(coursesToDelete.get(j))) {
                    findCourses.remove(findCourses.get(i));
                }
            }
        }
        return findCourses;
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

//    @Override
//    public List<Room> sortSchedule(List<Room> findRooms) {
//        List<Course> lichChan; // mảng tạm lưu khóa học có lịch chẵn
//        List<Course> lichLe; // mảng tạm lưu khóa học có lịch lẽ
//        List<Course> findCourses = courseService.sortCourseByBubbleSort(courseService.findAll());
//        Queue<Course> courseQueue = new ArrayDeque<Course>(); // hàng đợi lưu khóa học
//
//        for (Room room : findRooms) {
//            courseQueue.addAll(findCourses); // thêm các khóa học vào hàng đợi
//            for (Course course : courseQueue) {
//                // khởi tạo danh sách tạm
//                lichChan = new ArrayList<>();
//                lichLe = new ArrayList<>();
//
//                if (course.getIsScheduled().equals(false)) { // khóa học chưa khóa
//                    courseQueue.remove(course);
//                } else if ((room.getCapacity() - course.getAmount() >= 0L) // số học viên < chỗ ngồi
//                        && (room.getCapacity() - course.getAmount() < 10L) // chọn phòng phù hợp
//                        && course.getIsScheduled().equals(true)) { // khóa học đã khóa
//                    if (course.getSchedule().equals("1")) {
//                        if (!room.getLichChan().isEmpty()) // nếu phòng đã có khóa học thì bỏ qua
//                            break;
//                        else {
//                            lichChan.add(course); // thêm khóa học trong hàng đợi vào lichChan
//                            room.setLichChan(lichChan);
//                            roomRepository.save(room);
//                            courseQueue.remove(course);
//                        }
//                    } else if (course.getSchedule().equals("2")) {
//                        if (!room.getLichLe().isEmpty())
//                            break;
//                        else {
//                            lichLe.add(course);
//                            room.setLichLe(lichLe);
//                            roomRepository.save(room);
//                            courseQueue.remove(course);
//                        }
//                    } else {
//                        if (room.getLichLe().isEmpty()) {
//                            lichLe.add(course);
//                            room.setLichLe(lichLe);
//                            roomRepository.save(room);
//                        }
//                        if (room.getLichChan().isEmpty()) {
//                            lichChan.add(course);
//                            room.setLichChan(lichChan);
//                            roomRepository.save(room);
//                        }
//                        courseQueue.remove(course);
//                    }
//                }
//            }
//        }
//        return findRooms;
//    }


    // thuật toán lưu khóa học vào phòng học
    // ưu tiên khóa học ngắn hạn và khóa nhiều học viên
    // nếu khóa học có thời hạn bằng nhau thì ưu tiên số lượng học viên của mỗi khóa
    @Override
    public List<Room> sortSchedule(List<Room> findRooms) {
        List<Course> coursesToDelete = new ArrayList<>();
        sortRoom(findRooms); // sort desc room by capacity
        for (Room findRoom : findRooms) {
            List<Course> findCourses = courseService.findAll();
            deleteCoursesById(findCourses, coursesToDelete); // delete course after add course to room
            coursesToDelete.clear();
            courseService.sortCourseByGreedyAlgorithm(findCourses, 3); // sort course by greedy algorithm
            for (Course findCourse : findCourses) {
                List<Course> courseTemp = new ArrayList<>();
                if (findCourse.getIsScheduled().equals(false)) { // course is not locked
                    break;
                } else if (findCourse.getIsScheduled().equals(true)
                        && (findRoom.getCapacity() - findCourse.getAmount() >= 0L) // amount < capacity
                        && (findRoom.getCapacity() - findCourse.getAmount() < 10L)) { // capacity - amount < 10
                    // schedule = 1 => lichChan
                    // schedule = 2 => lichLe
                    // schedule = 3 => full
                    if (findCourse.getSchedule().equals("1")) {
                        if (!findRoom.getLichChan().isEmpty()) // if room not null, do not add the course
                            break;
                        else {
                            courseTemp.add(findCourse);
                            findRoom.setLichChan(courseTemp);
                            coursesToDelete.add(findCourse); // add course to coursesToDelete to remove course from findCourses
                            roomRepository.save(findRoom);
                        }
                    } else if (findCourse.getSchedule().equals("2")) {
                        if (!findRoom.getLichLe().isEmpty())
                            break;
                        else {
                            courseTemp.add(findCourse);
                            findRoom.setLichLe(courseTemp);
                            coursesToDelete.add(findCourse);
                            roomRepository.save(findRoom);
                        }
                    } else { // course has schedule = 3. if room has lichChan or lichLe null, add the course
                        if (findRoom.getLichLe().isEmpty()) {
                            courseTemp.add(findCourse);
                            findRoom.setLichLe(courseTemp);
                            roomRepository.save(findRoom);
                        }
                        if (findRoom.getLichChan().isEmpty()) {
                            courseTemp.add(findCourse);
                            findRoom.setLichChan(courseTemp);
                            roomRepository.save(findRoom);
                        }
                        coursesToDelete.add(findCourse);
                    }
                }
            }
        }
        return findRooms;
    }
}