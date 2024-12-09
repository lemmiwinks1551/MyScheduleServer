package com.lemmiwinks.myscheduleserver.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Data
@Entity
@Table(name = "CalendarDate")
public class CalendarDate {
    @Id
    private String syncUUID;

    private String userName;

    private Long syncTimestamp;

    private String syncStatus;

    @Transient
    private Integer _id;

    private String date;

    private String color;
}
