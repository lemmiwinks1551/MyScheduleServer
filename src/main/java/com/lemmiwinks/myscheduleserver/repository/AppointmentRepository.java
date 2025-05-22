package com.lemmiwinks.myscheduleserver.repository;

import com.lemmiwinks.myscheduleserver.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, String> {

    List<Appointment> findByUserName(String userName);

    Appointment findBySyncUUID(String syncUUID);

    @Query("SELECT MAX(a.syncTimestamp) FROM Appointment a WHERE a.userName = :username")
    Long findLastRemoteAppointmentTimestampByUsername(@Param("username") String username);

    @Query("SELECT a FROM Appointment a WHERE a.syncTimestamp > :syncTimestamp AND a.userName = :userName ORDER BY a.syncTimestamp ASC ")
    List<Appointment> findAppointmentAfterTimestamp(
            @Param("userName") String userName,
            @Param("syncTimestamp") Long timestamp
    );

    @Query("SELECT COUNT(a) FROM Appointment a WHERE a.userName = :userName")
    Long getCountByUser(
            @Param("userName") String userName
    );

    @Query("SELECT a FROM Appointment a WHERE a.userName = :userName AND a.deleted = false ORDER BY STR_TO_DATE(a.date, '%d.%m.%Y') DESC")
    List<Appointment> findByUserNameAndDeletedFalseOrderByDateDesc(@Param("userName") String userName);

}
