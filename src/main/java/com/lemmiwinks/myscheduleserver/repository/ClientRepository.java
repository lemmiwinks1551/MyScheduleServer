package com.lemmiwinks.myscheduleserver.repository;

import com.lemmiwinks.myscheduleserver.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, String> {
}
