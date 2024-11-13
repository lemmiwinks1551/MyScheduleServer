package com.lemmiwinks.myscheduleserver.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "Procedures")
public class Procedure {
    @Id
    private String uuid;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    private String status;

    private String procedureName;

    private BigDecimal procedurePrice;

    private String procedureNotes;
}