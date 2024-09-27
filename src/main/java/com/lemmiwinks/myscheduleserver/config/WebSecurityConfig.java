package com.lemmiwinks.myscheduleserver.config;

import com.lemmiwinks.myscheduleserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserService userService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // Отключение защиты от CSRF атак
                .csrf().disable()

                // Настройка авторизации запросов
                .authorizeRequests()

                // Доступ только для не зарегистрированных пользователей
                .antMatchers("/registration").not().fullyAuthenticated()

                // Доступ только для пользователей с ролью Администратор
                // .antMatchers("/admin/**").hasRole("ADMIN")

                /* Доступ только для пользователей с ролью USER
                .antMatchers("/news").hasRole("USER")
                .antMatchers("/news").hasRole("ADMIN")*/

                // Доступ разрешён всем пользователям:
                .antMatchers("/faq").permitAll()
                .antMatchers("/api/calendar/get-year/2024").permitAll()
                .antMatchers("/api/user-events").permitAll()
                .antMatchers("/confirm-account").permitAll()
                .antMatchers("/forgot-password").permitAll()
                .antMatchers("/password-reset").permitAll()
                .antMatchers("/message").permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/index").permitAll()

                // Доступ разрешён для всех ресурсов, если они есть
                .antMatchers("/resources/**").permitAll()

                // Все остальные страницы требуют аутентификации
                .anyRequest()
                .authenticated()

                .and()
                // Настройка формы входа
                    .formLogin()
                    .loginPage("/login") // Страница входа
                    .defaultSuccessUrl("/") // Перенаправление на главную страницу после успешного входа
                    .failureHandler(new CustomAuthenticationFailureHandler()) // кастомный обработчик
                    .failureUrl("/login?error")
                    .permitAll() // Разрешение доступа к странице входа
                .and()

                // Настройка выхода из системы
                .logout()
                .permitAll() // Разрешение доступа к выходу из системы
                .logoutSuccessUrl("/") // Перенаправление на главную страницу после выхода
        ;
    }

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());
    }
}
