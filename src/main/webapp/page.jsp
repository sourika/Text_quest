<html lang="ru" xml:lang="ru">
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Quest</title>
    <meta charset="UTF-8">
    <link href="static/main.css" rel="stylesheet">
</head>

<body>
<div class="main-container">
<h1 class="Forum-Regular-font-center">${question}</h1>

<c:if test="${error != null}">
    <p class="error-text">${error}</p>
</c:if>

<form action="${pageContext.request.contextPath}/logic" method="get">
    <label class="answer-label">
        <input type="radio" name="answer" value="answer1"> ${answer1}
    </label>
    <br>
    <label class="answer-label">
        <input type="radio" name="answer" value="answer2"> ${answer2}
    </label>
    <br>
    <br>
    <input type="submit" value="Ответить" class="my-button">
</form>


<br>
<br>
<br>

<table>
    <tr><td>Статистика:</td></tr>
    <tr><td>ID сессии: ${sessionId}</td></tr>
    <tr><td>Имя игрока: ${playerName}</td></tr>
    <tr><td>IP адрес: ${ipAddress}</td></tr>
    <tr><td>Номер игры: ${gameNumber}</td></tr>
</table>
</div>
</body>
</html>
