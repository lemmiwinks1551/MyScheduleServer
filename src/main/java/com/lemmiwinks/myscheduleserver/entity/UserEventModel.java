package com.lemmiwinks.myscheduleserver.entity;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class UserEventModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String sessionId;
    private String model;
    private String device;
    private String dateTime;
    private String appVersionName;
    private String eventType;
    private String event;
}
