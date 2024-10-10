package com.lemmiwinks.myscheduleserver.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtTokenFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;

    // Конструктор для инициализации JwtTokenProvider
    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    // Метод для выполнения фильтрации запросов
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        // Получаем токен из запроса с помощью метода resolveToken из jwtTokenProvider
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) servletRequest);

        try {
            // Если токен не null и валидный - проверяем его валидность
            if (token != null && jwtTokenProvider.validateToken(token)) {
                // Получаем аутентификацию из токена
                Authentication authentication = jwtTokenProvider.getAuthentication(token);

                // Если аутентификация не null
                if (authentication != null) {
                    // Устанавливаем аутентификацию в контекст безопасности
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (JwtAuthenticationException e) {
            // Если возникает исключение, очищаем контекст безопасности
            SecurityContextHolder.clearContext();
            // Отправляем ошибку в ответе с соответствующим статусом
            ((HttpServletResponse) servletResponse).sendError(e.getHttpStatus().value());
            // Пробрасываем исключение для дальнейшей обработки
            throw new JwtAuthenticationException("JWT token is expired or invalid");
        }

        // Продолжаем выполнение фильтров в цепочке
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
