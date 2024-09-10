package com.lemmiwinks.myscheduleserver.entity;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {
    // Этот класс должен реализовывать интерфейс GrantedAuthority,
    // в котором необходимо переопределить только один метод getAuthority() (возвращает имя роли).
    // Кроме конструктора по умолчанию необходимо добавить еще пару публичных конструкторов:
    // первый принимает только id, второй id и name.

    @Id
    private Long id;

    // Имя роли должно соответствовать шаблону: "«"ROLE_ИМЯ", например, ROLE_USER.
    private String name;

    // Поле users представляет пользователей, которым назначена данная роль.
    // Аннотация @Transient указывает, что это поле не будет сохранено в базе данных.
    // Оно используется для отображения отношения между ролями и пользователями, которые реализованы через связь "многие ко многим".
    // Аннотация mappedBy указывает, что связь уже описана в классе User на стороне поля "roles".
    @Transient
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    public Role() {
        // Конструктор по умолчанию необходим для JPA.
    }

    public Role(Long id) {
        // Конструктор, который позволяет создавать роль с указанным id.
        this.id = id;
    }

    public Role(Long id, String name) {
        // Конструктор, который позволяет создавать роль с указанными id и именем.
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public String getAuthority() {
        // Реализация метода getAuthority из интерфейса GrantedAuthority.
        // Возвращает имя роли, что необходимо для работы системы авторизации Spring Security.
        return getName();
    }
}
