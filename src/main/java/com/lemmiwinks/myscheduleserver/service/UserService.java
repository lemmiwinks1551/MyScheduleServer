package com.lemmiwinks.myscheduleserver.service;

import com.lemmiwinks.myscheduleserver.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    void saveUser(User user);

    void updateUser(User user);
}