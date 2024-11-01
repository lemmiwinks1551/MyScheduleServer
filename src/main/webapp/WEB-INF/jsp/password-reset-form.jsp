<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Сброс пароля</title>

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

        label {
            font-size: 0.9em;
            color: #333;
            display: block;
            margin-bottom: 8px;
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
            margin-top: 5px;
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
    <div class="form-container">
        <h2>Сброс пароля</h2>

        <!-- Форма для сброса пароля -->
        <form method="POST" action="/password-reset">
            <!-- Токен -->
            <input type="hidden" name="token" value="${token}">

            <!-- Поле для нового пароля -->
            <div class="form-group" style="position: relative;">
                <input id="password" name="newPassword" type="password" placeholder="Введите новый пароль" required>

                <!-- Иконка-глаз для показа/скрытия пароля -->
                <span id="togglePassword" style="position: absolute; right: -10px; top: 50%; transform: translateY(-50%); cursor: pointer;">👀️</span>
            </div>

            <!-- Подтверждение нового пароля -->
            <div class="form-group">
                <input id="newPasswordConfirm" name="newPasswordConfirm" type="password" placeholder="Введите новый пароль еще раз" required>
            </div>

            <!-- Сообщение об успехе -->
            <div class="success-message">${message}</div>

            <!-- Кнопка смены пароля -->
            <button type="submit">Сменить пароль</button>
        </form>
    </div>
</body>
</html>