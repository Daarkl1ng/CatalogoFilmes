<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">
    <title>Cadastrar Filme</title>
</head>
<body>

    <h1>Cadastrar novo filme</h1>

    <form action="${pageContext.request.contextPath}/filmes/novo" method="post">

        <p>
            <label for="titulo">Título:</label><br>
            <input type="text" id="titulo" name="titulo"
                   maxlength="255" required>
        </p>

        <p>
            <label for="diretor">Diretor:</label><br>
            <input type="text" id="diretor" name="diretor"
                   maxlength="255" required>
        </p>

        <p>
            <label for="anoLancamento">Ano de lançamento:</label><br>
            <input type="number" id="anoLancamento" name="anoLancamento"
                   min="1800" max="2100" required>
        </p>

        <p>
            <label for="genero">Gênero:</label><br>
            <input type="text" id="genero" name="genero"
                   maxlength="100" required>
        </p>

        <p>
            <label for="sinopse">Sinopse:</label><br>
            <textarea id="sinopse" name="sinopse"
                      rows="5" cols="40" maxlength="5000"></textarea>
        </p>

        <button type="submit">Cadastrar filme</button>

    </form>

    <p>
        <a href="${pageContext.request.contextPath}/filmes">
            Voltar ao catálogo
        </a>
    </p>

</body>
</html>