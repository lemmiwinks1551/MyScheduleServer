<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<!DOCTYPE html>
<html>
    <head>
      <meta charset="utf-8">
      <title>Восстановление пароля</title>

      <style>
        .error-message {
          color: red;
          font-size: 0.9em;
          margin-top: 0.5em;
        }
        .success-message {
          color: green;
          font-size: 0.9em;
          margin-top: 0.5em;
        }
      </style>

    </head>

    <body>

        <div>
            <form action="/forgot-password" method="POST">
              <h2>Восстановление пароля</h2>

              <div class="form-group">
                <label for="emailOrUsername">Введите Ваш Email или Логин:</label>
                <input id="emailOrUsername" name="emailOrUsername" type="text" placeholder="Email или Имя пользователя" required>
                <div class="error-message">${passwordError}</div>
                <div class="success-message">${successMessage}</div>
              </div>

              <button type="submit">Отправить письмо для сброса пароля</button>

              <h4><a href="/login">Вернуться на страницу входа</a></h4>
            </form>
        </div>

    </body>
</html>
