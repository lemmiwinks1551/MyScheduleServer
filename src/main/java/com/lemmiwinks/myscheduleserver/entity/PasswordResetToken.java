package com.lemmiwinks.myscheduleserver.entity;

import lombok.Data;
import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table(name = "passwordResetToken")
public class PasswordResetToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "token_id")
    private Long tokenId;

    @Column(name = "password_reset_token")
    private String passwordResetToken;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    public PasswordResetToken() {
    }

    public PasswordResetToken(User user) {
        this.user = user;
        createdDate = new Date();
        passwordResetToken = UUID.randomUUID().toString();
    }
}
