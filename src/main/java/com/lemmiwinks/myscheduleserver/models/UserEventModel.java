package com.lemmiwinks.myscheduleserver.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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

    public void setId(int id) {
        this.id = id;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public void setAppVersionName(String appVersionName) {
        this.appVersionName = appVersionName;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public void setEvent(String event) {
        this.event = event;
    }
}
