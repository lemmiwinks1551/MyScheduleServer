package com.lemmiwinks.myscheduleserver.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

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

    // конструкторы

    public PasswordResetToken() {}

    public PasswordResetToken(User user) {
        this.user = user;
        createdDate = new Date();
        passwordResetToken = UUID.randomUUID().toString();
    }

    // Getters

    public Long getTokenId() {
        return tokenId;
    }

    public String getConfirmationToken() {
        return passwordResetToken;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public User getUserEntity() {
        return user;
    }

    // Setters

    public void setTokenId(Long tokenId) {
        this.tokenId = tokenId;
    }

    public void setConfirmationToken(String confirmationToken) {
        this.passwordResetToken = confirmationToken;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
