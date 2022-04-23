package com.laptrinhjava.LichHoc.service.impl;

import com.laptrinhjava.LichHoc.entity.ERole;
import com.laptrinhjava.LichHoc.entity.Role;
import com.laptrinhjava.LichHoc.repository.RoleRepository;
import com.laptrinhjava.LichHoc.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Override
    public Optional<Role> findByName(ERole name) {
        return roleRepository.findByName(name);
    }
}
