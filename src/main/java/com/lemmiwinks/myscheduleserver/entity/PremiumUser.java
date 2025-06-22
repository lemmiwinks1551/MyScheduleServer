package com.lemmiwinks.myscheduleserver.entity;

import lombok.Data;

import javax.persistence.*;

@Table(name = "premium_users")
@Data
@Entity
public class PremiumUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    private Boolean isPremium;
}
