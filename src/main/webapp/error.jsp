<html lang="ru" xml:lang="ru">
<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <title>Error</title>
    <meta charset="UTF-8">
    <link href="static/main.css" rel="stylesheet">
</head>

<body>
<div class="main-container">
    <img src="${pageContext.request.contextPath}/static/images/evil.jpg" class="evil-image">
    <h1 class="Forum-Regular-font-center">Упсс... Похоже, темные силы наложили на тебя проклятие.<br>
        Ты забыл свое имя и цели путешествия. Чтобы вернуть себе воспоминания, <br>
        придется начать заново.</h1>
    <div class="center-buttons">
        <form action="${pageContext.request.contextPath}/game-control" method="get">
            <input type="hidden" name="action" value="quit">
            <input type="submit" value="Вернуть воспоминания" class="my-button">
        </form>
    </div>
</div>
</body>
</html>
