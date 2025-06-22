package com.lemmiwinks.myscheduleserver.repository;

import com.lemmiwinks.myscheduleserver.entity.User;
import com.lemmiwinks.myscheduleserver.entity.PremiumUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PremiumUserRepository extends JpaRepository<PremiumUser, Long> {
    Optional<PremiumUser> findByUser(User user);
}
