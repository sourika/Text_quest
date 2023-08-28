<html lang="ru" xml:lang="ru">
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>End</title>
    <meta charset="UTF-8">
    <link href="static/main.css" rel="stylesheet">
</head>

<body>
<div class="main-container">

    <c:if test="${isWin == true}">
        <img src="${pageContext.request.contextPath}/static/images/gold_key.png" class="key-image">
        <h1 class="Forum-Regular-font-center">Поздравляю! Ты прошел все испытания.
            Король преисполнен гордости вручить тебе ключ от комнаты мудрости.</h1>

        <div class="center-buttons">
        <form action="${pageContext.request.contextPath}/game-control" method="get" style="display: inline;">
            <input type="hidden" name="action" value="restart">
            <input type="submit" value="Начать заново" class="my-button">
        </form>

        <form action="${pageContext.request.contextPath}/game-control" method="get" style="display: inline;">
            <input type="hidden" name="action" value="quit">
            <input type="submit" value="Выйти из игры" class="my-button">
        </form>
        </div>
    </c:if>

    <c:if test="${isWin == false}">
        <img src="${pageContext.request.contextPath}/static/images/sad_king.png" class="king-image">
        <h1 class="Forum-Regular-font-center">О, нет...Ты потерпел неудачу. Но в другой раз у тебя все обязательно
            получится.</h1>

        <div class="center-buttons">
        <form action="${pageContext.request.contextPath}/game-control" method="get" style="display: inline;">
            <input type="hidden" name="action" value="restart">
            <input type="submit" value="Начать заново" class="my-button">
        </form>

        <form action="${pageContext.request.contextPath}/game-control" method="get" style="display: inline;">
            <input type="hidden" name="action" value="quit">
            <input type="submit" value="Выйти из игры" class="my-button">
        </form>
        </div>
    </c:if>
</div>
</body>
</html>
