package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.database.FilmeDAO;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Controla a exclusão de filmes do catálogo.
 *
 * Recebe o identificador do filme por uma requisição HTTP POST,
 * valida o valor informado e solicita a exclusão ao FilmeDAO.
 * Trata identificadores inválidos, filmes inexistentes e falhas SQL.
 */

@WebServlet("/filmes/excluir")
public class ExcluirFilmeServlet extends HttpServlet {

    /**
     * Processa uma solicitação de exclusão de filme.
     *
     * Valida o identificador recebido e solicita ao FilmeDAO a remoção
     * do registro correspondente. Em caso de sucesso, redireciona ao
     * catálogo; se o filme não existir ou ocorrer uma falha SQL,
     * retorna a resposta HTTP apropriada.
     *
     * @param request requisição HTTP contendo o identificador do filme
     * @param response resposta HTTP a ser enviada
     * @throws ServletException se ocorrer uma falha no processamento do Servlet
     * @throws IOException se ocorrer uma falha de entrada ou saída
     */

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String idTexto = request.getParameter("id");

        if (idTexto == null || idTexto.isBlank()) {
            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "O ID do filme é obrigatório."
            );
            return;
        }

        int id;

        try {
            id = Integer.parseInt(idTexto);
        } catch (NumberFormatException e) {
            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "O ID precisa ser um número válido."
            );
            return;
        }

        if (id <= 0) {
            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "O ID precisa ser positivo."
            );
            return;
        }

        FilmeDAO filmeDAO = new FilmeDAO();

        try {
            boolean excluido = filmeDAO.excluirFilme(id);

            if (excluido) {
                response.sendRedirect(request.getContextPath() + "/filmes");
            } else {
                response.sendError(
                        HttpServletResponse.SC_NOT_FOUND,
                        "Filme não encontrado para exclusão."
                );
            }

        } catch (SQLException e) {
            log("Erro ao excluir filme no banco de dados.", e);

            response.sendError(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Não foi possível excluir o filme. Tente novamente."
            );
        }
    }
}