<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<!DOCTYPE HTML>
<html lang="ru">
<head>
  <!-- Метатеги для мобильных устройств -->
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

  <link rel="shortcut icon" type="image/x-icon" href="/resources/static/favicon.ico" />

  <!-- Подключение Google Fonts -->
  <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap" rel="stylesheet">

  <!-- Устанавливаем заголовок страницы -->
  <title>Главная</title>

  <!-- Подключаем CSS-стили из ресурсов -->
  <link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/style.css">

  <!-- Встроенные стили для демонстрации -->
  <style>
    body {
      font-family: 'Roboto', sans-serif;
      margin: 0;
      padding: 0;
      background-color: #f0f0f0;
      color: #333;
      display: flex;
      flex-direction: column;
      min-height: 100vh;
    }

    .container {
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;
      flex: 1;
      padding: 20px;
      background: linear-gradient(135deg, #6e8efb, #a777e3);
      color: white;
      text-align: center;
    }

    h1, h3 {
      margin: 0;
    }

    .btn {
      display: inline-block;
      padding: 12px 24px;
      margin: 10px 0;
      background-color: #ffeb3b;
      color: #333;
      text-decoration: none;
      font-weight: 500;
      border-radius: 8px;
      transition: background-color 0.3s ease;
    }

    .btn:hover {
      background-color: #ffd600;
    }

    img {
      max-width: 150px;
      margin: 20px 0;
    }

    .footer {
      background-color: #333;
      color: white;
      padding: 10px 0;
      text-align: center;
    }

    .footer a {
      color: #ffeb3b;
      text-decoration: none;
    }

    @media (max-width: 768px) {
      .container {
        padding: 10px;
      }

      img {
        max-width: 100px;
      }
    }
  </style>
</head>

<body>
  <div class="container">
    <!-- Добавляем изображение -->
    <img src="/ic_launcher.png" alt="Логотип">

    <!-- Если пользователь не аутентифицирован, показываем ссылки для входа и регистрации -->
    <sec:authorize access="!isAuthenticated()">
      <h1>Добро пожаловать!</h1>
      <a href="/login" class="btn">Войти</a>
      <a href="/registration" class="btn">Зарегистрироваться</a>
    </sec:authorize>

    <!-- Если пользователь аутентифицирован, показываем ссылку на выход -->
    <sec:authorize access="isAuthenticated()">
      <h3>Добро пожаловать, ${pageContext.request.userPrincipal.name}!</h3>
      <a href="/account" class="btn">Мой аккаунт</a>
      <a href="/schedule" class="btn">Мои записи</a>
      <a href="/addAppointment" class="btn">Создать запись</a>
      <a href="/logout" class="btn">Выйти</a>
    </sec:authorize>
  </div>

  <!-- Почтовый адрес в подвале страницы -->
  <footer class="footer">
    <h4>Почтовый адрес для связи с разработчиками: <a href="mailto:scheduleapp@mail.ru">scheduleapp@mail.ru</a></h4>
    <h4><a href="/privacy-policy">Политика конфиденциальности</a></h4>
    <h4><a href="/disclaimer">Дисклеймер</a></h4>
  </footer>
</body>
</html>
