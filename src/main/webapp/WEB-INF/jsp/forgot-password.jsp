<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Восстановление пароля</title>

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

        @media (max-width: 768px) {
            .form-container {
                padding: 15px;
            }
        }
    </style>
</head>

<body>
    <div class="form-container">
        <form action="/forgot-password" method="POST">
            <h2>Восстановление пароля</h2>

            <!-- Поле для ввода email или имени пользователя -->
            <div class="form-group">
                <label for="emailOrUsername">Введите Ваш Email или Логин:</label>
                <input id="emailOrUsername" name="emailOrUsername" type="text" placeholder="Email или Имя пользователя" required>

                <!-- Сообщение об ошибке -->
                <div class="error-message">${passwordError}</div>

                <!-- Сообщение об успешной отправке -->
                <div class="success-message">${successMessage}</div>
            </div>

            <!-- Кнопка отправки -->
            <button type="submit">Отправить письмо для сброса пароля</button>
        </form>

        <!-- Ссылка на главную страницу -->
        <a href="/"> <button>На главную </button> </a>

    </div>
</body>
</html>