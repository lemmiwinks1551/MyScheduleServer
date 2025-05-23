<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<sec:authorize access="isAuthenticated()">
    <c:redirect url="/" />
</sec:authorize>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Вход в аккаунт</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap" rel="stylesheet">

    <!-- JS для отображения пароля -->
    <script src="${pageContext.request.contextPath}/resources/js/passwordToggle.js" defer></script>

    <style>
        body {
            font-family: 'Roboto', sans-serif;
            margin: 0;
            padding: 0;
            background: linear-gradient(135deg, #6e8efb, #a777e3);
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
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
            margin-top: 5px;
            text-align: right;
        }

        .forgot-password a {
            color: black;
            text-decoration: none;
        }

        .forgot-password a:hover {
            text-decoration: underline;
        }

        .back-home {
            display: block;
            text-align: center;
            margin-top: 15px;
            text-decoration: none;
            color: #333;
            font-weight: 500;
        }

        .back-home:hover {
            text-decoration: underline;
        }
    </style>
</head>

<body>
<div class="form-container">
    <form action="${pageContext.request.contextPath}/login" method="POST">
        <h2>Вход в систему</h2>

        <!-- Имя пользователя -->
        <div class="form-group">
            <input id="username" name="username" type="text" placeholder="Имя пользователя" required>
        </div>

        <!-- Пароль -->
        <div class="form-group" style="position: relative;">
            <input id="password" name="password" type="password" placeholder="Пароль" required>
            <span id="togglePassword" style="position: absolute; right: -10px; top: 50%; transform: translateY(-50%); cursor: pointer;">👀️</span>
        </div>

        <!-- Ошибка входа -->
        <c:if test="${not empty param.error}">
            <p class="error">
                Не удалось выполнить вход в аккаунт.<br />
                <c:out value="${sessionScope['SPRING_SECURITY_LAST_EXCEPTION'].message}" />
            </p>
        </c:if>

        <!-- Кнопка входа -->
        <button type="submit">Войти</button>

        <!-- Забыли пароль -->
        <div class="forgot-password">
            <a href="${pageContext.request.contextPath}/forgot-password">Забыл пароль</a>
        </div>
    </form>

    <!-- Вернуться на главную -->
    <a class="back-home" href="${pageContext.request.contextPath}/">← На главную</a>
</div>
</body>
</html>