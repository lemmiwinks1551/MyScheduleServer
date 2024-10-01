<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Мой аккаунт</title>

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

        .account-container {
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

        p {
            font-size: 1em;
            color: #333;
            margin-bottom: 15px;
        }

        .status-message {
            font-size: 0.9em;
            margin-top: 10px;
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
            margin-top: 10px;
            margin-bottom: 10px;
        }

        button:hover {
            background-color: #ffd600;

        }

        .verification-container p {
            margin-bottom: 10px;
            color: #333;
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

        @media (max-width: 768px) {
            .account-container {
                padding: 15px;
            }
        }
    </style>
</head>

<body>
    <div class="account-container">
        <h2>Мой аккаунт</h2>

        <!-- Информация о пользователе -->
        <div>
            <p><strong>Имя пользователя:</strong> ${username}</p>
            <p><strong>Email:</strong> ${userEmail}</p>
        </div>

        <!-- Статус верификации -->
        <div class="status-message" style="color: ${verificationColor};">
            ${isVerified}
        </div>

        <!-- Форма для повторной отправки письма подтверждения -->
        <c:if test="${showResendButton}">
            <form action="/resend-confirmation-email" method="POST" class="verification-container">

                <!-- Сообщение о статусе отправки токена -->
                <p style="color: ${resendTokenStatusMsgColor};">
                    ${resendTokenStatusMsg}
                </p>

                <!-- Кнопка отправки -->
                <button type="submit">Отправить письмо</button>

                <!-- Токен -->
                <p>${token}</p>
            </form>
        </c:if>

        <!-- Ссылка на главную страницу -->
        <a href="/"> <button>На главную </button> </a>
    </div>
</body>
</html>