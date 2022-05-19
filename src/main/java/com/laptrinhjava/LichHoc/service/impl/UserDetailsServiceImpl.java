package com.laptrinhjava.LichHoc.service.impl;

import com.laptrinhjava.LichHoc.entity.User;
import com.laptrinhjava.LichHoc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  @Autowired
  UserService userService;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
    User user = userService.findByUserName(userName)
        .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + userName));
    return UserDetailsImpl.build(user);
  }
}
