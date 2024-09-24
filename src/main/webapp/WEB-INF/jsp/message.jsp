<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<!DOCTYPE html>
<html>
    <head>
        <link rel="shortcut icon" type="image/x-icon" href="/resources/static/favicon.ico" />
        <meta charset="utf-8">
        <title>Сообщение</title>
    </head>

    <body>
        <div>
            <h1>${message}</h1>

            <a href="/"><br>На главную<br></a>
            <a href="/login"><br>Вход<br></a>
        </div>
    </body>
</html>
