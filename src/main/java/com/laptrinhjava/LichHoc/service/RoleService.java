package com.laptrinhjava.LichHoc.service;

import com.laptrinhjava.LichHoc.entity.ERole;
import com.laptrinhjava.LichHoc.entity.Role;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface RoleService {
    Optional<Role> findByName(ERole name);
}
