package com.lemmiwinks.myscheduleserver.repository;

import com.lemmiwinks.myscheduleserver.entity.CalendarDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CalendarDateRepository extends JpaRepository<CalendarDate, String> {

    List<CalendarDate> findByUserName(String userName);

    CalendarDate findBySyncUUID(String syncUUID);

    @Query("SELECT MAX(a.syncTimestamp) FROM CalendarDate a WHERE a.userName = :username")
    Long findLastRemoteCalendarDateTimestampByUsername(@Param("username") String username);

    @Query("SELECT a FROM CalendarDate a WHERE a.syncTimestamp > :syncTimestamp AND a.userName = :userName ORDER BY a.syncTimestamp ASC ")
    List<CalendarDate> findCalendarDateAfterTimestamp(
            @Param("userName") String userName,
            @Param("syncTimestamp") Long timestamp
    );

    @Query("SELECT COUNT(a) FROM CalendarDate a WHERE a.userName = :userName")
    Long getCountByUser(
            @Param("userName") String userName
    );
}
