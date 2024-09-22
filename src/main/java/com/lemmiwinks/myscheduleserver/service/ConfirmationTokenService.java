package com.lemmiwinks.myscheduleserver.service;

import com.lemmiwinks.myscheduleserver.entity.ConfirmationToken;
import com.lemmiwinks.myscheduleserver.entity.User;

public interface ConfirmationTokenService {
    void saveToken(User user);

    void updateCreatedDate(ConfirmationToken confirmationToken);

    void updateToken(ConfirmationToken confirmationToken);

    void sendVerificationEmail(User user);
}
