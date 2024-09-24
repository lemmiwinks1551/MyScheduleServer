package com.lemmiwinks.myscheduleserver.repository;

import com.lemmiwinks.myscheduleserver.entity.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    PasswordResetToken findByPasswordResetToken(String passwordResetToken);

    PasswordResetToken findByUserId(Long userId);
}
