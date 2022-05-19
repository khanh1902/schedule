package com.laptrinhjava.LichHoc.service.impl;

import com.laptrinhjava.LichHoc.entity.Course;
import com.laptrinhjava.LichHoc.entity.Room;
import com.laptrinhjava.LichHoc.repository.CourseRepository;
import com.laptrinhjava.LichHoc.repository.RoomRepository;
import com.laptrinhjava.LichHoc.service.CourseService;
import com.laptrinhjava.LichHoc.service.RoomService;
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

    // thuật toán lưu khóa học vào phòng học
    // ưu tiên khóa học ngắn hạn và khóa ít học viên
    // nếu khóa học có thời hạn bằng nhau thì ưu tiên số lượng học viên của mỗi khóa
    @Override
    public List<Room> sortSchedule(List<Room> findRooms) {
        List<Course> lichChan; // mảng tạm lưu khóa học có lịch chẵn
        List<Course> lichLe; // mảng tạm lưu khóa học có lịch lẽ
        List<Course> findCourses = courseService.sortCourseByBubbleSort(courseService.findAll());
        Queue<Course> courseQueue = new ArrayDeque<Course>(); // hàng đợi lưu khóa học

        for (Room room : findRooms) {
            courseQueue.addAll(findCourses); // thêm các khóa học vào hàng đợi
            for (Course course : courseQueue) {
                // khởi tạo danh sách tạm
                lichChan = new ArrayList<>();
                lichLe = new ArrayList<>();

                if (course.getIsScheduled().equals(false)) { // khóa học chưa khóa
                    courseQueue.remove(course);
                } else if ((room.getCapacity() - course.getAmount() >= 0L) // số học viên < chỗ ngồi
                        && (room.getCapacity() - course.getAmount() < 10L) // chọn phòng phù hợp
                        && course.getIsScheduled().equals(true)) { // khóa học đã khóa
                    if (course.getSchedule().equals("1")) {
                        if (!room.getLichChan().isEmpty()) // nếu phòng đã có khóa học thì bỏ qua
                            break;
                        else {
                            lichChan.add(course); // thêm khóa học trong hàng đợi vào lichChan
                            room.setLichChan(lichChan);
                            roomRepository.save(room);
                            courseQueue.remove(course);
                        }
                    } else if (course.getSchedule().equals("2")) {
                        if (!room.getLichLe().isEmpty())
                            break;
                        else {
                            lichLe.add(course);
                            room.setLichLe(lichLe);
                            roomRepository.save(room);
                            courseQueue.remove(course);
                        }
                    } else {
                        if (room.getLichLe().isEmpty()) {
                            lichLe.add(course);
                            room.setLichLe(lichLe);
                            roomRepository.save(room);
                        }
                        if (room.getLichChan().isEmpty()) {
                            lichChan.add(course);
                            room.setLichChan(lichChan);
                            roomRepository.save(room);
                        }
                        courseQueue.remove(course);
                    }
                }

            }
        }
        return findRooms;
    }
}
