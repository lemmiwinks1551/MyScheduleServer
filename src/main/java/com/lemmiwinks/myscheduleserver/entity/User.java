package com.lemmiwinks.myscheduleserver.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users") // название таблицы, в которой будут храниться данные о пользователях
public class User implements UserDetails { // Чтобы в дальнейшим использовать класс User в Spring Security, он должен реализовывать интерфейс UserDetails

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // генерацией id будет заниматься БД
    private Long id;

    @Size(min = 2, message = "Не меньше 2 знаков")
    private String username;

    private String userEmail;

    @Size(min = 8, message = "Не меньше 8 знаков")
    private String password;

    @Transient // не имеет отображения в БД
    // Поле, показывающее, что пароль введен корректно
    private String passwordConfirm;

    private boolean inEnabled;

    // Конструктор по умолчанию
    public User() {
    }

    // Конструктор с параметрами
    public User(Long id, String username, String userEmail, String password, String passwordConfirm, boolean inEnabled) {
        this.id = id;
        this.username = username;
        this.userEmail = userEmail;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
        this.inEnabled = inEnabled;
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

    public void setUsername(@Size(min = 2, message = "Не меньше 2 знаков") String username) {
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

    public void setPassword(@Size(min = 8, message = "Не меньше 8 знаков") String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {return passwordConfirm;}

    public void setPasswordConfirm(String passwordConfirm) {this.passwordConfirm = passwordConfirm;}

    public boolean isInEnabled() {
        return inEnabled;
    }

    public void setEnabled(boolean inEnabled) {
        this.inEnabled = inEnabled;
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
