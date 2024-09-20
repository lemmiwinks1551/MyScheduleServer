package com.lemmiwinks.myscheduleserver.config;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        String errorMessage = "Неизвестная ошибка";

        if (exception.getMessage().equalsIgnoreCase("Bad credentials")) {
            errorMessage = "Неверный логин или пароль";
        }

        // Передаем параметр "error" и "message" в URL
        request.setAttribute("message", errorMessage);
        request.setAttribute("error", true);
    }
}
