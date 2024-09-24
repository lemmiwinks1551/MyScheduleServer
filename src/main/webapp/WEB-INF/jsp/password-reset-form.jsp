<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<!DOCTYPE html>
<html>
    <head>
      <meta charset="utf-8">
      <title>Сброс пароля</title>

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

        <div class="form-group">
        <form method="POST" action="/password-reset">
                <input type="hidden" name="token" value="${token}">

                <div
                    <label for="newPassword">Новый пароль:</label>
                    <input id="newPassword" name="newPassword" type="password" placeholder="Введите новый пароль" required class="input-field">
                    <div class="error-message">${passwordError}</div>

                </div>

                <div
                    <label for="newPasswordConfirm">Подтверждение пароля:</label>
                    <input id="newPasswordConfirm" name="newPasswordConfirm" type="password" placeholder="Введите новый пароль еще раз" required class="input-field">
                    <div class="error-message">${passwordError}</div>

                </div>

                    <div class="success-message">${message}</div>

                <button type="submit">Сменить пароль</button>
            </form>
        </div>

      <h4><a href="/">На главную</a></h4>

    </body>

</html>