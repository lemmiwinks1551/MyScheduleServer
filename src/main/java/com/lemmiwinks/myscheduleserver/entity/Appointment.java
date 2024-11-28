package com.lemmiwinks.myscheduleserver.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "Appointments")
public class Appointment {

    @Id
    private String syncUUID;

    private Long localAppointmentId;

    private String userName;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "DATETIME(3)")
    private Date syncTimestamp;

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