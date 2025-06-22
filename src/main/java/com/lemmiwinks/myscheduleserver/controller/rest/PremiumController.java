package com.lemmiwinks.myscheduleserver.controller.rest;

import com.lemmiwinks.myscheduleserver.entity.PremiumUser;
import com.lemmiwinks.myscheduleserver.entity.User;
import com.lemmiwinks.myscheduleserver.entity.dto.PremiumStatusDto;
import com.lemmiwinks.myscheduleserver.repository.PremiumUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class PremiumController {

    private final PremiumUserRepository premiumUserRepository;

    // Установить/обновить статус
    @PostMapping("/premium-status")
    public ResponseEntity<?> setPremiumStatus(
            @AuthenticationPrincipal User user,
            @RequestBody PremiumStatusDto dto) {

        Optional<PremiumUser> optional = premiumUserRepository.findByUser(user);

        if (optional.isPresent()) {
            PremiumUser premiumUser = optional.get();

            if (!dto.getIsPremium().equals(premiumUser.getIsPremium())) {
                premiumUser.setIsPremium(dto.getIsPremium());
                premiumUserRepository.save(premiumUser);
            }
        } else {
            PremiumUser newPremiumUser = new PremiumUser();
            newPremiumUser.setUser(user);
            newPremiumUser.setIsPremium(dto.getIsPremium());
            premiumUserRepository.save(newPremiumUser);
        }

        return ResponseEntity.ok().build();
    }
}

