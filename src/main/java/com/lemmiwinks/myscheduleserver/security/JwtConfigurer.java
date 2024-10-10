package com.lemmiwinks.myscheduleserver.security;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
public class JwtConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final JwtTokenFilter jwtTokenFilter;

    // Конструктор для инициализации JwtTokenFilter
    public JwtConfigurer(JwtTokenFilter jwtTokenFilter) {
        this.jwtTokenFilter = jwtTokenFilter;
    }

    @Override
    // Метод конфигурирует HttpSecurity и добавляет JwtTokenFilter в цепочку фильтров
    public void configure(HttpSecurity httpSecurity) {
        // Добавляем JwtTokenFilter перед фильтром UsernamePasswordAuthenticationFilter,
        // чтобы токен проверялся до стандартной аутентификации по логину и паролю
        httpSecurity.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
