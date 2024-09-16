package com.lemmiwinks.myscheduleserver.repository;

import com.lemmiwinks.myscheduleserver.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByUserEmail(String userEmail);

    User findUserByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByUserEmail(String email);
}
