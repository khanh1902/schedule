package com.laptrinhjava.LichHoc.service.impl;

import com.laptrinhjava.LichHoc.entity.User;
import com.laptrinhjava.LichHoc.repository.UserRepository;
import com.laptrinhjava.LichHoc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User findUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public Optional<User> findByUserName(String userName) {
        return Optional.ofNullable(userRepository.findByUserName(userName));
    }

    @Override
    public List<User> findAllByUserName(String userName) {
        return userRepository.findAllByUserName(userName);
    }

    @Override
    public Boolean existsByUserName(String userName) {
        return userRepository.existsByUserName(userName);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }
}
