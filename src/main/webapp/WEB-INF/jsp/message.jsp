<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Сообщение</title>

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
            flex-direction: column; /* чтобы кнопка была под сообщением */
        }

        .message-container {
            background-color: white;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
            text-align: center;
            width: 100%;
            max-width: 400px;
        }

        h1 {
            font-size: 1.5em;
            color: #333;
            margin-bottom: 20px;
        }

        .btn {
            display: inline-block;
            padding: 12px 24px;
            margin-top: 20px;
            background-color: #ffeb3b;
            color: #333;
            border: none;
            border-radius: 8px;
            font-weight: 500;
            font-size: 1em;
            cursor: pointer;
            transition: background-color 0.3s ease;
            text-align: center;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        }

        .btn:hover {
            background-color: #ffd600;
        }

        @media (max-width: 768px) {
            .message-container {
                padding: 15px;
            }
        }
    </style>
</head>

<body>
    <div class="message-container">
        <h1>${message}</h1>
        <button type="button" class="btn" onclick="window.location.href='/'">На главную</button>
    </div>
</body>
</html>
