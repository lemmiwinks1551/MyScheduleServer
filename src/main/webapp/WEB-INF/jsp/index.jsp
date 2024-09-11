<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<!DOCTYPE HTML>
<html>
<head>
  <link rel="shortcut icon" type="image/x-icon" href="/resources/static/favicon.ico" />

  <!-- Устанавливаем заголовок страницы -->
  <title>Главная</title>

  <!-- Добавляем изображение -->
  <img src="/resources/static/ic_launcher.png" />

  <!-- Указываем кодировку и тип содержимого страницы -->
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

  <!-- Подключаем CSS-стили из ресурсов -->
  <link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/style.css">
</head>

<body>
<div>

  <!-- Если пользователь не аутентифицирован, показываем ссылки для входа и регистрации -->
  <sec:authorize access="!isAuthenticated()">
  <h3>Добро пожаловать!</h3>
    <h4><a href="/login">Войти</a></h4>
    <h4><a href="/registration">Зарегистрироваться</a></h4>
  </sec:authorize>

  <!-- Если пользователь аутентифицирован, показываем ссылку на выход -->
  <sec:authorize access="isAuthenticated()">
    <h3>Добро пожаловать, ${pageContext.request.userPrincipal.name}!</h3>
    <h4><a href="/logout">Выйти</a></h4>
  </sec:authorize>

  <!-- Ссылка на раздел FAQ, доступен всем пользователям -->
  <h4><a href="/faq/view">FAQ</a></h4>
</div>
</body>
</html>
