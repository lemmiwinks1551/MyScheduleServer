<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<spring:eval expression="@environment.getProperty('SMARTCAPTCHA_CLIENT_KEY')" var="SMARTCAPTCHA_CLIENT_KEY" />

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Регистрация</title>

    <script src="https://smartcaptcha.yandexcloud.net/captcha.js" defer></script>
    <link rel="shortcut icon" type="image/x-icon" href="/resources/static/favicon.ico" />

    <!-- Подключение Google Fonts -->
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap" rel="stylesheet">

    <style>
        body {
            font-family: 'Roboto', sans-serif;
            background-color: #f0f0f0;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            background: linear-gradient(135deg, #6e8efb, #a777e3);
        }

        .form-container {
            background-color: white;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 400px;
        }

        h2 {
            text-align: center;
            margin-bottom: 20px;
            color: #333;
        }

        .form-group {
            margin-bottom: 15px;
            width: 95%;
        }

        input {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
            font-size: 1em;
        }

        .error-message {
            color: red;
            font-size: 0.9em;
            margin-top: 5px;
        }

        .success-message {
            color: green;
            font-size: 0.9em;
            margin-top: 0.5em;
            text-align: center;
        }

        button {
            width: 100%;
            padding: 12px;
            background-color: #ffeb3b;
            border: none;
            border-radius: 8px;
            color: #333;
            font-weight: 500;
            font-size: 1em;
            cursor: pointer;
            transition: background-color 0.3s ease;
            margin-top: 0.5em;
            margin-bottom: 0.5em;
        }

        button:hover {
            background-color: #ffd600;
        }

        h4 {
            text-align: center;
            margin-top: 20px;
        }

        h4 a {
            color: #ffeb3b;
            text-decoration: none;
        }

        h4 a:hover {
            text-decoration: underline;
        }

        .captcha-container {
            margin: 15px 0;
        }

        @media (max-width: 768px) {
            .form-container {
                padding: 15px;
            }
        }
    </style>
</head>

<body>
    <div class="form-container">
        <form:form method="POST" modelAttribute="userForm" id="registrationForm">
            <h2>Регистрация нового пользователя</h2>

            <!-- Логин -->
            <div class="form-group">
                <form:input type="text" path="username" placeholder="Введите логин"></form:input>
                <form:errors path="username" cssClass="error-message"></form:errors>
                <div class="error-message">${usernameError}</div>
            </div>

            <!-- Email -->
            <div class="form-group">
                <form:input type="text" path="userEmail" placeholder="Email"></form:input>
                <form:errors path="userEmail" cssClass="error-message"></form:errors>
                <div class="error-message">${userEmailError}</div>
            </div>

            <!-- Пароль -->
            <div class="form-group">
                <form:input type="password" path="password" placeholder="Пароль"></form:input>
                <form:errors path="password" cssClass="error-message"></form:errors>
                <div class="error-message">${passwordError}</div>
            </div>

            <!-- Подтверждение пароля -->
            <div class="form-group">
                <form:input type="password" path="passwordConfirm" placeholder="Введите пароль еще раз"></form:input>
                <form:errors path="passwordConfirm" cssClass="error-message"></form:errors>
                <div class="error-message">${passwordError}</div>
            </div>

            <!-- Капча -->
            <div id="captcha-container" class="captcha-container smart-captcha" data-sitekey="${SMARTCAPTCHA_CLIENT_KEY}"></div>
            <div class="error-message">${captchaError}</div>

            <!-- Сообщение об успехе -->
            <h1 class="success-message">${message}</h1>

            <!-- Кнопка отправки формы -->
            <button type="submit">Зарегистрироваться</button>
        </form:form>

        <!-- Ссылка на главную -->
        <a href="/"> <button>На главную </button> </a>

    </div>
</body>
</html>
