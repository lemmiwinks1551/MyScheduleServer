package com.lemmiwinks.myscheduleserver.repositiry;

import com.lemmiwinks.myscheduleserver.models.FaqModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FaqRepository extends JpaRepository<FaqModel, Integer> {
}
