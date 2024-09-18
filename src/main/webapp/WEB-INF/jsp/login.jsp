<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<!DOCTYPE html>
<html>
<head>
  <link rel="shortcut icon" type="image/x-icon" href="/resources/static/favicon.ico" />
  <meta charset="utf-8">
  <title>Вход в аккаунт</title>

  <style>
    .error-message {
      color: red;
      font-size: 0.9em;
      margin-top: 0.5em;
    }
  </style>

</head>

<body>
<sec:authorize access="isAuthenticated()">
  <% response.sendRedirect("/"); %>
</sec:authorize>

<div>
  <form:form method="POST" modelAttribute="loginForm">
    <h2>Вход в систему</h2>

    <div>
      <form:input path="username" placeholder="Имя пользователя" autofocus="true"></form:input>
      <form:errors path="username" cssClass="error-message"></form:errors>
      <div class="error-message">${usernameError}</div>
    </div>

    <div>
      <form:input path="password" type="password" placeholder="Пароль"></form:input>
      <form:errors path="password" cssClass="error-message"></form:errors>
      <div class="error-message">${passwordError}</div>
    </div>

    <div>
      <button type="submit">Войти</button>
      <h4><a href="/">На главную</a></h4>
    </div>
  </form:form>
</div>

</body>
</html>
