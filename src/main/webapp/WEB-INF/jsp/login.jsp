<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Вход в аккаунт</title>

    <!-- Подключение Google Fonts -->
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap" rel="stylesheet">
    <script src="${contextPath}/resources/js/passwordToggle.js" defer></script>

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

        .error {
            color: red;
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
        }

        button:hover {
            background-color: #ffd600;
        }

        .forgot-password {
            margin-top: 10px;
            text-align: right;
            margin-top: 5px;
            margin-bottom: 5px;
        }

        .forgot-password a {
            color: black;
            text-decoration: none;
        }

        .forgot-password a:hover {
            text-decoration: underline;
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
            .form-container {
                padding: 15px;
            }
        }
    </style>
</head>

<body>
    <!-- Если пользователь уже аутентифицирован, перенаправляем на главную -->
    <sec:authorize access="isAuthenticated()">
      <% response.sendRedirect("/"); %>
    </sec:authorize>

    <div class="form-container">
        <form action="/login" method="POST">
            <h2>Вход в систему</h2>

            <!-- Поле для ввода имени пользователя -->
            <div class="form-group">
                <input id="username" name="username" type="text" placeholder="Имя пользователя" required>
            </div>

            <!-- Поле для ввода пароля -->
            <div class="form-group" style="position: relative;">
                <!-- Поле для ввода пароля -->
                <input id="password" name="password" type="password" placeholder="Пароль" >

                <!-- Иконка-глаз для показа/скрытия пароля -->
                <span id="togglePassword" style="position: absolute; right: -10px; top: 50%; transform: translateY(-50%); cursor: pointer;">👀️</span>
            </div>

            <!-- Кнопка для входа -->
            <button type="submit">Войти</button>

            <!-- Ссылка для восстановления пароля -->
            <div class="forgot-password">
                <a href="/forgot-password">Забыл пароль</a>
            </div>
        </form>

        <!-- Ссылка на главную страницу -->
        <a href="/"> <button>На главную </button> </a>

        <!-- Сообщение об ошибке при неправильных данных для входа -->
        <% if (request.getParameter("error") != null) { %>
            <p class="error">
                Не удалось выполнить вход в аккаунт.<br />
                Ошибка: ${SPRING_SECURITY_LAST_EXCEPTION.message}
            </p>
        <% } %>
    </div>
</body>
</html>