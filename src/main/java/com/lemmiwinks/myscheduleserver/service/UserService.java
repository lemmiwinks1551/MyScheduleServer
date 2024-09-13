package com.lemmiwinks.myscheduleserver.service;

import com.lemmiwinks.myscheduleserver.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    void saveUser(User user);

    ResponseEntity<?> confirmEmail(String confirmationToken);
}