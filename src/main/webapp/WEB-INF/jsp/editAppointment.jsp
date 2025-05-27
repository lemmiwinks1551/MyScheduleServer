<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Редактировать запись</title>

    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap" rel="stylesheet" />
    <style>
        body {
            font-family: 'Roboto', sans-serif;
            background: linear-gradient(135deg, #6e8efb, #a777e3);
            margin: 0;
            padding: 0;
        }
        .container {
            max-width: 600px;
            margin: 40px auto;
            background-color: #fff;
            padding: 30px 25px;
            border-radius: 12px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
        }
        h1 {
            text-align: center;
            color: #333;
            margin-bottom: 25px;
            font-weight: 700;
        }
        form {
            display: flex;
            flex-direction: column;
            gap: 18px;
        }
        label {
            font-weight: 500;
            color: #444;
            margin-bottom: 6px;
        }
        input[type="text"], input[type="date"], input[type="time"], input[type="number"], textarea {
            padding: 10px 12px;
            font-size: 15px;
            border: 1px solid #ccc;
            border-radius: 6px;
            width: 100%;
            box-sizing: border-box;
            font-family: 'Roboto', sans-serif;
        }
        textarea {
            resize: vertical;
            min-height: 80px;
            font-size: 14px;
        }
        .buttons {
            display: flex;
            justify-content: space-between;
            margin-top: 30px;
            gap: 10px;
        }
        button {
            flex: 1 1 45%;
            padding: 12px;
            font-size: 16px;
            font-weight: 600;
            border-radius: 8px;
            border: none;
            cursor: pointer;
            font-family: 'Roboto', sans-serif;
        }
        .btn-save {
            background-color: #6e8efb;
            color: white;
        }
        .btn-save:hover {
            background-color: #5973d1;
        }
        .btn-cancel {
            background-color: #ccc;
            color: #333;
        }
        .btn-cancel:hover {
            background-color: #b3b3b3;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Редактировать запись</h1>
    <form action="updateAppointment" method="post">
        <input type="hidden" name="syncUUID" value="${appointment.syncUUID}" />

        <div>
            <label for="day">День</label>
            <input type="date" id="day" name="day" value="${formattedDate}" required />
        </div>

        <div>
            <label for="time">Время</label>
            <input type="time" id="time" name="time" value="${appointment.time}" />
        </div>

        <div>
            <label for="clientName">Имя клиента</label>
            <input type="text" id="clientName" name="clientName" value="${appointment.name}" />
        </div>

        <div>
            <label for="phone">Номер телефона</label>
            <input type="text" id="phone" name="phone" value="${appointment.phone}" />
        </div>

        <div>
            <label for="vk">ВКонтакте</label>
            <input type="text" id="vk" name="vk" value="${appointment.vk}" />
        </div>

        <div>
            <label for="telegram">Telegram</label>
            <input type="text" id="telegram" name="telegram" value="${appointment.telegram}" />
        </div>

        <div>
            <label for="instagram">Instagram</label>
            <input type="text" id="instagram" name="instagram" value="${appointment.instagram}" />
        </div>

        <div>
            <label for="whatsapp">WhatsApp</label>
            <input type="text" id="whatsapp" name="whatsapp" value="${appointment.whatsapp}" />
        </div>

        <div>
            <label for="clientNotes">Заметки клиента</label>
            <textarea id="clientNotes" name="clientNotes">${appointment.clientNotes}</textarea>
        </div>

        <div>
            <label for="procedure">Услуга</label>
            <input type="text" id="procedure" name="procedure" value="${appointment.procedure}" />
        </div>

        <div>
            <label for="price">Цена</label>
            <input type="number" id="price" name="price" min="0" step="0.01" value="${appointment.procedurePrice}" />
        </div>

        <div>
            <label for="appointmentNotes">Заметки записи</label>
            <textarea id="appointmentNotes" name="appointmentNotes">${appointment.notes}</textarea>
        </div>

        <div class="buttons">
            <button type="submit" class="btn-save">Сохранить</button>
            <button type="button" class="btn-cancel" onclick="window.location.href='/schedule'">Отмена</button>
        </div>
    </form>
</div>
</body>
</html>
