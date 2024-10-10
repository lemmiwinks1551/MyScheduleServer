package com.lemmiwinks.myscheduleserver.security;

import com.lemmiwinks.myscheduleserver.service.UserService;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider {

    // Секретный ключ для подписей токенов
    @Value("${jwt.secret}")
    private String secretKey;

    // Время жизни токена
    @Value("${jwt.expiration}")
    private long validityMs;

    //
    @Value("${jwt.header}")
    private String authorizationHeader;

    private final UserService userService;

    public JwtTokenProvider(UserService userService) {
        this.userService = userService;
    }

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    // Метод для создания JWT токена
    public String createToken(String username, String email) {
        // Создаем объект Claims, который содержит информацию о пользователе
        Claims claims = Jwts.claims()
                .setSubject(username);

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityMs * 1000);

        // Формируем токен, подписываем его секретным ключом и возвращаем
        return Jwts.builder()
                .setClaims(claims)                                  // Информация о пользователе
                .setIssuedAt(now)                                   // Время выдачи токена
                .setExpiration(validity)                            // Время действия токена
                .signWith(SignatureAlgorithm.HS512, secretKey)      // Подпись токена с использованием HS512 и секретного ключа
                .compact();                                         // Упаковка в строку
    }

    // Метод для валидации (проверки) JWT-токена
    public boolean validateToken(String token) {
        try {
            // Парсим и проверяем токен, используя секретный ключ для верификации подписи
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);

            // Проверяем, истек ли срок действия токена
            return !claimsJws.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            // Если токен недействителен или истек, выбрасываем исключение
            throw new JwtAuthenticationException("JWT token is expired or invalid");
        }
    }

    // Метод для получения аутентификации из токена
    public Authentication getAuthentication(String token) {
        // Загружаем детали пользователя по имени пользователя, извлеченному из токена
        UserDetails userDetails = this.userService.loadUserByUsername(getUsername(token));

        // Возвращаем объект аутентификации, который включает в себя пользовательские данные и права доступа
        return new UsernamePasswordAuthenticationToken(userService, "", userDetails.getAuthorities());
    }

    // Метод для получения username из JWT
    public String getUsername(String token) {
        // Парсим токен, используя секретный ключ, и извлекаем имя пользователя (subject) из его полезной нагрузки
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    // Метод предназначен для извлечения JWT токена из HTTP-заголовка запроса
    public String resolveToken(HttpServletRequest request) {
        // Метод принимает объект HttpServletRequest, который представляет собой запрос, поступивший на сервер.
        // Этот объект содержит всю информацию о запросе, включая заголовки, параметры и тело запроса.

        // Получаем токен аутентификации из заголовка запроса с именем, указанным в authorizationHeader
        return request.getHeader(authorizationHeader);
    }
}
