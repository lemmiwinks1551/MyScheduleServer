package com.lemmiwinks.myscheduleserver.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "CalendarDate")
public class CalendarDate {
    @Id
    private String syncUUID;

    private String userName;

    private Long syncTimestamp;

    private String syncStatus;

    private Integer _id;

    private String date;

    private String color;
}
