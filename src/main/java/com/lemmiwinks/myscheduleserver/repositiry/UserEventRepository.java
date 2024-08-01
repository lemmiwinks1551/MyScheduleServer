package com.lemmiwinks.myscheduleserver.repositiry;

import com.lemmiwinks.myscheduleserver.models.UserEventModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserEventRepository extends JpaRepository<UserEventModel, Integer> {
}
