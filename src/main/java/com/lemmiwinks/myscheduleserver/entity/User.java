package com.lemmiwinks.myscheduleserver.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;

@Data
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

    private boolean isEnabled = true;

    private boolean syncEnabled = false;

    private boolean betaTester = false;

    public void setEmailVerified(Boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public Boolean getEmailVerified() {
        return emailVerified;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
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
