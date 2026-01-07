@import gg.jte.Content

@param Content content

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Учебный проект</title>

</head>
<body>
<!-- Навигация -->
<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
    <div class="container">
        <a class="navbar-brand" href="/">Hexlet App</a>
        <div class="navbar-nav">
            <a class="nav-link" href="/">Главная</a>
            <a class="nav-link" href="/users">Пользователи</a>
        </div>
    </div>
</nav>

<!-- Основной контент -->
<main class="container mt-4">
    ${content}
</main>

</body>
</html>
