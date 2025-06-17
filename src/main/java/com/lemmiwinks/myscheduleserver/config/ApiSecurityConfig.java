package com.lemmiwinks.myscheduleserver.config;

import com.lemmiwinks.myscheduleserver.security.JwtConfigurer;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@Order(1)   // Приоритет применения конфигурации
public class ApiSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtConfigurer jwtConfigurer;

    // JWT-фильтр
    public ApiSecurityConfig(JwtConfigurer jwtConfigurer) {
        this.jwtConfigurer = jwtConfigurer;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // Область действия конфигурации только URL, начинающимися с /api/**
                .antMatcher("/api/**")
                .csrf().disable()

                // Указываем, что не должно быть HTTP-сессий — каждый запрос должен быть самостоятельным (stateless)
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()

                // Настраиваем доступ:
                .authorizeRequests()

                // Разрешаем доступ к:
                .antMatchers(
                        "/api/v1/auth/login", // логину
                        "/api/v1/auth/forgot-password", // восстановлению пароля
                        "/api/v1/auth/resend-confirmation-email", // отправке почты для подтверждения
                        "/api/v1/registration" //регистрации
                ).permitAll()

                // Все остальные API-запросы требуют аутентификации
                .anyRequest().authenticated()

                .and()

                // Фильтр JWT
                .apply(jwtConfigurer);
    }
}

