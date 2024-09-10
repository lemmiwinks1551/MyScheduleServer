package com.lemmiwinks.myscheduleserver.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "users") // название таблицы, в которой будут храниться данные о пользователях
public class User implements UserDetails {
    // Чтобы в дальнейшим использовать класс User в Spring Security, он должен реализовывать интерфейс UserDetails

    public User() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // генерацией id будет заниматься БД
    private Long id;

    @Size(min=2, message = "Не меньше 2 знаков")
    private String username;

    @Size(min=8, message = "Не меньше 8 знаков")
    private String password;

    @Transient // не имеет отображения в БД
    private String passwordConfirm;

    // @многие ко многим - один пользователь может иметь несколько ролей и у одной роли может быть несколько пользователей
    // жадная загрузка, т.е. список ролей загружается вместе с пользователем сразу (не ждет пока к нему обратятся)
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(@Size(min = 2, message = "Не меньше 2 знаков") String username) {
        this.username = username;
    }

    public void setPassword(@Size(min = 8, message = "Не меньше 8 знаков") String password) {
        this.password = password;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

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

    // возвращает список ролей пользователя
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }
}
