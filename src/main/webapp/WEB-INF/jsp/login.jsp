<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<!DOCTYPE html>
<html>
<head>
  <link rel="shortcut icon" type="image/x-icon" href="/resources/static/favicon.ico" />
  <meta charset="utf-8">
  <title>Вход в аккаунт</title>
</head>

<body>
<sec:authorize access="isAuthenticated()">
  <% response.sendRedirect("/"); %>
</sec:authorize>
<div>
  <form method="POST" action="/login">
    <h2>Вход в систему</h2>
    <div>
      <input name="username" type="text" placeholder="Имя пользователя"
             autofocus="true"/>
      <input name="password" type="password" placeholder="Пароль"/>
      <button type="submit">Войти</button>
      <h4><a href="/registration">Зарегистрироваться</a></h4>
    </div>
  </form>
</div>

</body>
</html>
