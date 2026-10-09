<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">
    <title>Editar Filme</title>
</head>
<body>

    <h1>Editar filme</h1>

    <form action="${pageContext.request.contextPath}/filmes/editar" method="post">

        <input type="hidden" name="id" value="<c:out value='${filme.id}' />">

        <p>
            <label for="titulo">Título:</label><br>
            <input type="text" id="titulo" name="titulo"
                   maxlength="255" required
                   value="<c:out value='${filme.titulo}' />">
        </p>

        <p>
            <label for="diretor">Diretor:</label><br>
            <input type="text" id="diretor" name="diretor"
                   maxlength="255" required
                   value="<c:out value='${filme.diretor}' />">
        </p>

        <p>
            <label for="anoLancamento">Ano de lançamento:</label><br>
            <input type="number" id="anoLancamento" name="anoLancamento"
                   min="1800" max="2100" required
                   value="<c:out value='${filme.anoLancamento}' />">
        </p>

        <p>
            <label for="genero">Gênero:</label><br>
            <input type="text" id="genero" name="genero"
                   maxlength="100" required
                   value="<c:out value='${filme.genero}' />">
        </p>

        <p>
            <label for="sinopse">Sinopse:</label><br>
            <textarea id="sinopse" name="sinopse"
                      rows="5" cols="40"
                      maxlength="5000"><c:out value="${filme.sinopse}" /></textarea>
        </p>

        <button type="submit">Salvar alterações</button>

    </form>

    <p>
        <a href="${pageContext.request.contextPath}/filmes">
            Cancelar e voltar ao catálogo
        </a>
    </p>

</body>
</html>