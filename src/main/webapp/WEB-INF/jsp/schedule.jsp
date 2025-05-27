<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Мои записи</title>

    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap" rel="stylesheet">

    <style>
        body {
            font-family: 'Roboto', sans-serif;
            background: linear-gradient(135deg, #6e8efb, #a777e3);
            margin: 0;
            padding: 0;
        }

        .container {
            max-width: 1000px;
            margin: 40px auto;
            background-color: #fff;
            padding: 20px;
            border-radius: 12px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
        }

        h1 {
            text-align: center;
            color: #333;
            margin-bottom: 20px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        thead {
            background-color: #f5f5f5;
        }

        th, td {
            padding: 12px;
            border: 1px solid #ddd;
            text-align: left;
        }

        tr:nth-child(even) {
            background-color: #f9f9f9;
        }

        th {
            font-weight: 500;
        }

        @media (max-width: 768px) {
            th, td {
                font-size: 0.9em;
                padding: 8px;
            }
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Мои записи</h1>
    <table>
        <thead>
            <tr>
                <th>Дата</th>
                <th>Время</th>
                <th>Клиент</th>
                <th>Телефон</th>
                <th>Процедура</th>
                <th>Цена</th>
                <th>Заметки</th>

                <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">

                <th style="text-align: center;">
                    <i class="bi bi-three-dots-vertical" style="font-size: 20px;"></i>
                </th>

            </tr>
        </thead>
        <tbody>
            <c:forEach var="appointment" items="${appointments}">
                <tr>
                    <td>${appointment.date}</td>
                    <td>${appointment.time}</td>
                    <td>${appointment.name}</td>
                    <td>${appointment.phone}</td>
                    <td>${appointment.procedure}</td>
                    <td>${appointment.procedurePrice}</td>
                    <td>${appointment.notes}</td>

                    <td>
                        <!-- Ссылка на редактирование -->
                        <a href="editAppointment?syncUUID=${appointment.syncUUID}" style="margin-right: 8px;">
                            <i class="bi bi-pencil-fill" style="font-size: 18px; color: #333;"></i>
                        </a>

                        <!-- Кнопка удаления -->
                        <form action="deleteAppointment" method="post" style="display:inline;" onsubmit="return confirm('Удалить запись?');">
                            <input type="hidden" name="syncUUID" value="${appointment.syncUUID}" />
                            <button type="submit" style="background:none; border:none; cursor:pointer;">
                                <i class="bi bi-trash-fill" style="font-size: 18px; color: #d00;"></i>
                            </button>
                        </form>
                    </td>

                </tr>
            </c:forEach>
        </tbody>

    </table>
</div>
</body>
</html>
