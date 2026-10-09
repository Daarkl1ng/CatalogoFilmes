<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">
    <title>Catálogo de Filmes</title>
</head>
<body>

    <h1>Catálogo de Filmes</h1>
    <p>
        <a href="${pageContext.request.contextPath}/filmes/novo">
            Cadastrar novo filme
        </a>
    </p>

    <form action="${pageContext.request.contextPath}/filmes" method="get">
        <label for="busca">Buscar por título ou diretor:</label>

        <input
            type="text"
            id="busca"
            name="busca"
            maxlength="255"
            value="<c:out value='${param.busca}' />"
            placeholder="Ex.: Matrix ou Christopher Nolan">

        <button type="submit">Buscar</button>
    </form>

    <c:choose>
        <c:when test="${empty filmes}">
            <c:choose>
                <c:when test="${not empty param.busca}">
                    <p>
                        Nenhum filme encontrado para o termo:
                        <c:out value="${param.busca}" />.
                    </p>
                </c:when>
                <c:otherwise>
                    <p>Nenhum filme cadastrado.</p>
                </c:otherwise>
            </c:choose>
        </c:when>

        <c:otherwise>
            <table border="1">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Título</th>
                        <th>Diretor</th>
                        <th>Ano</th>
                        <th>Gênero</th>
                        <th>Sinopse</th>
                        <th>Ações</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="filme" items="${filmes}">
                        <tr>
                            <td><c:out value="${filme.id}" /></td>
                            <td><c:out value="${filme.titulo}" /></td>
                            <td><c:out value="${filme.diretor}" /></td>
                            <td><c:out value="${filme.anoLancamento}" /></td>
                            <td><c:out value="${filme.genero}" /></td>
                            <td><c:out value="${filme.sinopse}" /></td>
                            <td>
                                <a href="${pageContext.request.contextPath}/filmes/detalhes?id=${filme.id}">
                                    Ver detalhes
                                </a>
                                |
                                <a href="${pageContext.request.contextPath}/filmes/editar?id=${filme.id}">
                                    Editar
                                </a>
                                <form action="${pageContext.request.contextPath}/filmes/excluir"
                                      method="post"
                                      onsubmit="return confirm('Tem certeza de que deseja excluir este filme?');">

                                    <input type="hidden" name="id" value="<c:out value='${filme.id}' />">

                                    <button type="submit">Excluir</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:otherwise>
    </c:choose>

</body>
</html>