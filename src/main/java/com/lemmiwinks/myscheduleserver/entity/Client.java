package com.lemmiwinks.myscheduleserver.entity;

import lombok.Data;
import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "Clients")
public class Client {
    @Id
    private String uuid;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    private String status;

    private String clientName;

    private String clientPhone;

    private String clientTelegram;

    private String clientInstagram;

    private String clientVk;

    private String clientWhatsapp;

    private String clientNotes;

    private String clientPhoto;
}