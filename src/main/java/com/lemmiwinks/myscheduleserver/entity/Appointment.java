package com.lemmiwinks.myscheduleserver.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "Appointments")
public class Appointment {

    @Id
    private String syncUUID;

    private Long localAppointmentId;

    private String userName;

    private Long syncTimestamp;

    private String syncStatus;

    private String appointmentDate;
    private String appointmentTime;
    private String appointmentNotes;

    // Client-related fields
    private String clientId;
    private String clientName;
    private String clientPhone;
    private String clientTelegram;
    private String clientInstagram;
    private String clientVk;
    private String clientWhatsapp;
    private String clientNotes;
    private String clientPhoto;

    // Procedure-related fields
    private String procedureId;
    private String procedureName;
    private Integer procedurePrice;
    private String procedureNotes;
}