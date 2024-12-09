package com.lemmiwinks.myscheduleserver.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Appointments")
public class Appointment {

    // Sync fields
    @Id
    private String syncUUID;
    private String userName;
    private Long syncTimestamp;
    private String syncStatus;

    // Appointment fields
    @Transient
    private Long _id;
    private String date;
    private String time;
    private String notes;
    private Boolean deleted;

    // Client fields
    private Long clientId = null;
    private String name;
    private String phone;
    private String telegram;
    private String instagram;
    private String vk;
    private String whatsapp;
    private String clientNotes;
    private String photo;

    // Procedure fields
    @Column(name = "`procedure`")
    private String procedure;
    private String procedurePrice;
    private String procedureNotes;
}