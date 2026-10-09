<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">
    <title>Detalhes do Filme</title>
</head>
<body>

    <h1>Detalhes do Filme</h1>

    <h2><c:out value="${filme.titulo}" /></h2>

    <p>
        <strong>Diretor:</strong>
        <c:out value="${filme.diretor}" />
    </p>

    <p>
        <strong>Ano de lançamento:</strong>
        <c:out value="${filme.anoLancamento}" />
    </p>

    <p>
        <strong>Gênero:</strong>
        <c:out value="${filme.genero}" />
    </p>

    <p>
        <strong>Sinopse:</strong><br>
        <c:out value="${filme.sinopse}" />
    </p>

    <p>
        <a href="${pageContext.request.contextPath}/filmes">
            Voltar ao catálogo
        </a>
    </p>

</body>
</html>