package com.lemmiwinks.myscheduleserver.repository;

import com.lemmiwinks.myscheduleserver.entity.Procedure;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcedureRepository extends JpaRepository<Procedure, String> {
}
