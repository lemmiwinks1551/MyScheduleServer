<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Мои записи</title>

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

        .action-menu {
            position: relative;
            display: inline-block;
        }

        .action-button {
            background: none;
            border: none;
            font-size: 20px;
            cursor: pointer;
            padding: 0;
        }

        .dropdown-content {
            display: none;
            position: absolute;
            right: 0;
            z-index: 1;
            background-color: white;
            min-width: 100px;
            box-shadow: 0 2px 6px rgba(0,0,0,0.15);
            border-radius: 6px;
            overflow: hidden;
        }

        .dropdown-content a,
        .dropdown-content form {
            display: block;
            padding: 8px 12px;
            text-decoration: none;
            color: #333;
            background: white;
            border: none;
            width: 100%;
            text-align: left;
        }

        .dropdown-content a:hover,
        .dropdown-content form:hover {
            background-color: #f0f0f0;
        }

        .show {
            display: block;
        }

        @media (max-width: 768px) {
            th, td {
                font-size: 0.9em;
                padding: 8px;
            }
        }
    </style>

<script>
    function toggleDropdown(id) {
        const dropdown = document.getElementById(id);
        document.querySelectorAll(".dropdown-content").forEach(dd => {
            if (dd.id !== id) {
                dd.classList.remove("show");
            }
        });
        dropdown.classList.toggle("show");
    }

    window.addEventListener("click", function(e) {
        if (!e.target.closest(".action-menu")) {
            document.querySelectorAll(".dropdown-content").forEach(dd => dd.classList.remove("show"));
        }
    });
</script>

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
            <th style="text-align: center;">Действие</th>
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
                <td style="text-align: center;">
                    <div class="action-menu">
                        <button class="action-button" onclick="toggleDropdown('dropdown-${appointment.syncUUID}')">
                            ⋮
                        </button>
                        <div id="dropdown-${appointment.syncUUID}" class="dropdown-content">
                        <a href="editAppointment?syncUUID=${appointment.syncUUID}">Редактировать</a>
                        <form action="deleteAppointment" method="post" onsubmit="return confirm('Удалить запись?');" style="margin: 0; padding: 0;">
                            <input type="hidden" name="syncUUID" value="${appointment.syncUUID}" />
                            <button type="submit" style="all: unset; cursor: pointer; display: block; width: 100%; padding: 8px 12px; color: #333; text-align: left;">
                                Удалить
                            </button>
                        </form>
                        </div>
                    </div>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
