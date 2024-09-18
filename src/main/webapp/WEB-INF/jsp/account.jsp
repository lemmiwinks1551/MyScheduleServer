<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<!DOCTYPE html>
<html>
    <head>
      <meta charset="utf-8">
      <title>Мой аккаунт</title>

      <style>
            .form-group {
            margin-top: 0.5em;
            margin-bottom: 0.5em;
            }
       </style>

    <h2>Мой аккаунт</h2>

    </head>

    <body>
        <form:form method="POST" modelAttribute="userForm">
            <div>
                <p>Имя пользователя: ${username}</p>
                </div>

                <div>
                    <p>Email: ${userEmail}</p>
                </div>

                <div>
                    <p style="color: ${verificationColor};">${isVerified}</p>
                </div>
        </form:form>
        <h4><a href="/">На главную</a></h4>
    </body>

</html>