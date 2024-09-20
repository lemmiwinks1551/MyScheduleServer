<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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

                <form action="/resend-confirmation-email" method="POST">

                    <p style="color: ${verificationColor};">${isVerified}</p>

                    <c:if test="${showResendButton}">
                        <div style="border: 2px solid red; padding: 10px; display: inline-block;">
                            <p>Отправить письмо для подтверждения аккаунта на мою почту (один раз в 24 часа)</p>
                            <p style="color: ${resendTokenStatusMsgColor};">${resendTokenStatusMsg}</p>
                            <button type="submit">Отправить письмо</button>
                            <p>${token}</p>
                        </div>
                    </c:if>
                </form>

        </form:form>
        <h4><a href="/">На главную</a></h4>
    </body>

</html>