<html lang="ru" xml:lang="ru">
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Prolog</title>
    <meta charset="UTF-8">
    <link href="static/main.css" type="text/css" rel="stylesheet">
</head>

<body>
<div class="main-container">
<h1 class="Forum-Regular-font">Пролог</h1>

<p class="prolog-text">В далекой сказочной стране стоит загадочный дворец, хранящий сверхъестественные знания. <br>
    Лишь тот, кто пройдет череду таинственных испытаний, сможет встретиться с королем и <br>
    получить ключ от комнаты мудрости. <br>
    Только ты, отважный герой, можешь определить исход этого увлекательного приключения. <br>
    Твоя смекалка, решимость и смелость помогут тебе правильно ответить на все вопросы. Однако <br>
    будь осторожен, каждый неправильный ответ имеет темные последствия и риск потерять все, <br>
    даже жизнь.</p>

<h1 class="Forum-Regular-font">Знакомство</h1>

<p class="prolog-text">В этой сказочной стране в мире и согласии живут эльфы и гномы. Эльфы заботятся о природе, <br>
    а гномы создают волшебные артефакты.<br>
    Король этой сказочной страны - добрый правитель, обладающий несказанной мудростью. Врата города<br>
    охраняет могущественный великан, готовый защитить его от любой угрозы.<br>
    Но надо быть начеку! В пещере обитает дракон и лучше бы тебе его не тревожить. А под землей <br>
    прячутся магические существа, которые могут быть опасными для тебя, но если ты им понравишься, <br>
    они помогут тебе в трудную минуту. <br>
    Итак, мой отважный герой, если ты не испугался и принимаешь вызов судьбы, назови свое имя.</p>

<form action="${pageContext.request.contextPath}/start" method="get">
    <input type="text" id="playerName" name="playerName" class="text-field"/>
    <input type="submit" value="Ввод" class="my-button"/>
</form>

<c:if test="${not empty error}">
    <p class="error-text">${error}</p>
</c:if>
</div>
</body>
</html>