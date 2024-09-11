<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<html>
<head>
    <link rel="shortcut icon" type="image/x-icon" href="/resources/static/favicon.ico" />
    <title>Часто задаваемые вопросы</title>
</head>
<body>
    <h2>Часто задаваемые вопросы</h2>
    <table border="1">
        <tr>
            <th>№</th>
            <th>Вопрос</th>
            <th>Ответ</th>
        </tr>
        <c:forEach var="faq" items="${faqModels}">
            <tr>
                <td>${faq.id}</td>
                <td>${faq.question}</td>
                <td>${faq.answer}</td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>