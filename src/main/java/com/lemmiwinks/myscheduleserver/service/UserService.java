package com.lemmiwinks.myscheduleserver.service;

import com.lemmiwinks.myscheduleserver.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.ui.Model;

public interface UserService extends UserDetailsService {

    void saveUser(User user);

    Boolean confirmEmail(String confirmationToken);
}