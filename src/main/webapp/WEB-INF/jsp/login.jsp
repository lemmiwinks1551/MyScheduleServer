<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<!DOCTYPE html>
<html>
    <head>
      <meta charset="utf-8">
      <title>Вход в аккаунт</title>

      <style>
        .form-group {
        margin-top: 0.5em;
        margin-bottom: 0.5em;
        }
        .error {
            color: red;
            margin-top: 1em;
        }
      </style>

    </head>

    <body>
    <sec:authorize access="isAuthenticated()">
      <% response.sendRedirect("/"); %>
    </sec:authorize>

        <div>
            <form action="/login" method="POST">
              <h2>Вход в систему</h2>

              <div class="form-group">
                <input id="username" name="username" type="text" placeholder="Имя пользователя" required>
              </div>

              <div class="form-group">
                <input id="password" name="password" type="password" placeholder="Пароль" required>
              </div>

              <button type="submit">Войти</button>

              <div class="forgot-password">
                <a href="/forgot-password">Забыл пароль?</a>
              </div>

              <h4><a href="/">На главную</a></h4>

            </form>
        </div>

        <% if (request.getParameter("error") != null) { %>
            <p style="color:red">
                Не удалось выполнить вход в аккаунт.<br />
                Ошибка: ${SPRING_SECURITY_LAST_EXCEPTION.message}
            </p>
        <% } %>

    </body>
</html>
