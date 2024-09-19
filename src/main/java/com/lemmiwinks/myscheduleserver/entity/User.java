package com.lemmiwinks.myscheduleserver.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "users") // название таблицы, в которой будут храниться данные о пользователях
public class User implements UserDetails { // Чтобы в дальнейшим использовать класс User в Spring Security, он должен реализовывать интерфейс UserDetails

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // генерацией id будет заниматься БД
    private Long id;

    private String username;

    private String userEmail;

    private boolean emailVerified;

    private String password;

    @Transient // не имеет отображения в БД
    // Поле, показывающее, что пароль введен корректно
    private String passwordConfirm;

    private boolean isEnabled = false;

    // Конструктор по умолчанию
    public User() {
    }

    public void setEmailVerified(Boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public Boolean getEmailVerified() {
        return emailVerified;
    }

    // Конструктор с параметрами
    public User(Long id, String username, String userEmail, Boolean emailVerified, String password, String passwordConfirm, boolean isEnabled) {
        this.id = id;
        this.username = username;
        this.userEmail = userEmail;
        this.emailVerified = emailVerified;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
        this.isEnabled = isEnabled;
    }

    // Геттеры и сеттеры для полей
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public void setEnabled(boolean inEnabled) {
        this.isEnabled = inEnabled;
    }

    // Реализация методов из UserDetails для интеграции с Spring Security
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
