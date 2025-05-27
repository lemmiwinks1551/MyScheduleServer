<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Добавить запись</title>

    <!-- Google Fonts -->
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

        input[type="text"],
        input[type="date"],
        input[type="time"],
        input[type="number"],
        textarea {
            padding: 10px 12px;
            font-size: 15px;
            border: 1px solid #ccc;
            border-radius: 6px;
            outline-offset: 2px;
            outline-color: transparent;
            transition: outline-color 0.2s ease-in-out;
            width: 100%;
            box-sizing: border-box;
            font-family: 'Roboto', sans-serif;
        }

        input:focus,
        textarea:focus {
            outline-color: #6e8efb;
            border-color: #6e8efb;
        }

        textarea {
            resize: vertical;
            min-height: 80px;
            font-size: 14px;
        }

        .buttons {
            display: flex;
            justify-content: space-between;
            flex-wrap: wrap;
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
            transition: background-color 0.25s ease;
        }

        .btn-add {
            background-color: #6e8efb;
            color: white;
        }

        .btn-add:hover {
            background-color: #5973d1;
        }

        .btn-cancel {
            background-color: #ccc;
            color: #333;
        }

        .btn-cancel:hover {
            background-color: #b3b3b3;
        }

        @media (max-width: 640px) {
            .container {
                margin: 20px 10px;
                padding: 20px 15px;
            }

            input[type="text"],
            input[type="date"],
            input[type="time"],
            input[type="number"],
            textarea {
                font-size: 14px;
                padding: 9px 10px;
            }

            h1 {
                font-size: 22px;
            }

            button {
                font-size: 15px;
            }
        }
    </style>
</head>


<body>
<div class="container">
    <h1>Добавить запись</h1>
    <form action="createAppointment" method="post">
        <!-- Общая инфо -->
        <div>
            <label for="day">День</label>
            <input type="date" id="day" name="day" required />
        </div>

        <div>
            <label for="time">Время</label>
            <input type="time" id="time" name="time" />
        </div>

        <!-- Инфа о клиенте -->
        <div>
            <label for="clientName">Имя клиента</label>
            <input type="text" id="clientName" name="clientName" />
        </div>

        <div>
            <label for="phone">Номер телефона</label>
            <input type="tel" id="phone" name="phone" placeholder="+7 (___) ___-__-__" />
        </div>

        <div>
            <label for="vk">ВКонтакте</label>
            <input type="text" id="vk" name="vk" placeholder="Ссылка на ВК" />
        </div>

        <div>
            <label for="telegram">Telegram</label>
            <input type="text" id="telegram" name="telegram" placeholder="Ссылка на Telegram" />
        </div>

        <div>
            <label for="instagram">Instagram</label>
            <input type="text" id="instagram" name="instagram" placeholder="Ссылка на Instagram" />
        </div>

        <div>
            <label for="whatsapp">WhatsApp</label>
            <input type="text" id="whatsapp" name="whatsapp" placeholder="Ссылка на WhatsApp" />
        </div>

        <div>
            <label for="clientNotes">Заметки клиента</label>
            <textarea id="clientNotes" name="clientNotes" placeholder="Примечания о клиенте"></textarea>
        </div>

        <!-- Инфа о процедуре -->
        <div>
            <label for="procedure">Услуга</label>
            <input type="text" id="procedure" name="procedure" />
        </div>

        <div>
            <label for="price">Цена</label>
            <input type="number" id="price" name="price" min="0" step="0.01" />
        </div>

        <!-- Заметки записи -->
        <div>
            <label for="appointmentNotes">Заметки записи</label>
            <textarea id="appointmentNotes" name="appointmentNotes" placeholder="Дополнительные заметки"></textarea>
        </div>

        <div class="buttons">
            <button type="submit" class="btn-add" >Добавить</button>
            <button type="button" class="btn-cancel" onclick="window.location.href='/'">На главную</button>
        </div>
    </form>
</div>
</body>
</html>
