package com.lemmiwinks.myscheduleserver.config;

import com.lemmiwinks.myscheduleserver.security.JwtConfigurer;
import com.lemmiwinks.myscheduleserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserService userService;

    private final JwtConfigurer jwtConfigurer;

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    public WebSecurityConfig(JwtConfigurer jwtConfigurer) {
        this.jwtConfigurer = jwtConfigurer;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // Отключение защиты от CSRF атак
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // Настройка авторизации запросов
                .authorizeRequests()

                // Доступ только для не зарегистрированных пользователей
                .antMatchers("/registration").not().fullyAuthenticated()

                // Доступ разрешён всем:
                .antMatchers("/faq").permitAll()
                .antMatchers("/api/calendar/get-year/2024").permitAll()
                .antMatchers("/api/user-events").permitAll()
                .antMatchers("/confirm-account").permitAll()
                .antMatchers("/forgot-password").permitAll()
                .antMatchers("/password-reset").permitAll()
                .antMatchers("/message").permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/actuator/prometheus/").permitAll()
                .antMatchers("/api/v1/auth/login").permitAll()
                .antMatchers("/api/v1/registration").permitAll()
                .antMatchers("/api/v1/auth/forgot-password").permitAll()
                .antMatchers("/api/v1/auth/resend-confirmation-email").permitAll()
                .antMatchers("/api/v1/user-data/check-auth").permitAll()

                // Доступ разрешён для всех ресурсов, если они есть
                .antMatchers("/resources/**").permitAll()
                .antMatchers("/privacy-policy").permitAll()
                .antMatchers("/disclaimer").permitAll()

                // Все остальные страницы требуют аутентификации
                .anyRequest()
                .authenticated()

                .and()
                .apply(jwtConfigurer);
    }
}