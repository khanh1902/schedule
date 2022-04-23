package com.laptrinhjava.LichHoc.service;

import com.laptrinhjava.LichHoc.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {
    User findUserName(String userName);
    Optional<User> findByUserName(String userName);
    List<User> findAllByUserName(String userName);
    Boolean existsByUserName(String userName);
    User save(User user);
}
