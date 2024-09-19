<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<!DOCTYPE html>
<html>
<head>
  <link rel="shortcut icon" type="image/x-icon" href="/resources/static/favicon.ico" />
  <meta charset="utf-8">
  <title>Регистрация</title>

  <style>
    .error-message {
      color: red;
      font-size: 0.9em;
      margin-top: 0.5em;
    }
    .success-message {
      color: green;
      font-size: 0.9em;
      margin-top: 0.5em;
    }
  </style>

</head>

<body>
<div>
  <form:form method="POST" modelAttribute="userForm">
    <h2>Регистрация нового пользователя</h2>

    <div>
      <form:input type="text" path="username" placeholder="Имя пользователя"></form:input>
      <form:errors path="username" cssClass="error-message"></form:errors>
      <div class="error-message">${usernameError}</div>
    </div>

    <div>
      <form:input type="text" path="userEmail" placeholder="Email"></form:input>
      <form:errors path="userEmail" cssClass="error-message"></form:errors>
      <div class="error-message">${userEmailError}</div>
    </div>

    <div>
      <form:input type="password" path="password" placeholder="Пароль"></form:input>
      <form:errors path="password" cssClass="error-message"></form:errors>
      <div class="error-message">${passwordError}</div>
    </div>

    <div>
      <form:input type="password" path="passwordConfirm" placeholder="Введите пароль еще раз"></form:input>
      <form:errors path="password" cssClass="error-message"></form:errors>
      <div class="error-message">${passwordError}</div>
    </div>

    <h1 class="success-message">${message}</h1>

    <button type="submit">Зарегистрироваться</button>
  </form:form>
  <h4><a href="/">На главную</a></h4>

</div>
</body>
</html>
