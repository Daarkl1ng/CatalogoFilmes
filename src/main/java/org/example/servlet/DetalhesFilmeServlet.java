package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.database.FilmeDAO;
import org.example.model.Filme;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Controla a visualização dos detalhes de um filme.
 *
 * Recebe o identificador pela requisição HTTP, consulta o filme
 * por meio do FilmeDAO e encaminha seus dados para detalhes.jsp.
 * Trata identificadores inválidos, filmes inexistentes e falhas SQL.
 */

@WebServlet("/filmes/detalhes")
public class DetalhesFilmeServlet extends HttpServlet {

    /**
     * Processa a consulta dos detalhes de um filme.
     *
     * Valida o identificador recebido, busca o registro no banco
     * e encaminha o objeto Filme para a página de detalhes.
     *
     * @param request requisição HTTP contendo o identificador do filme
     * @param response resposta HTTP a ser enviada
     * @throws ServletException se ocorrer uma falha no processamento do Servlet
     * @throws IOException se ocorrer uma falha de entrada ou saída
     */

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

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

        Filme filme;

        try {
            FilmeDAO filmeDAO = new FilmeDAO();
            filme = filmeDAO.buscarPorId(id);
        } catch (SQLException e) {
            log("Erro ao buscar filme por ID.", e);

            response.sendError(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Não foi possível carregar os detalhes do filme. Tente novamente."
            );
            return;
        }

        if (filme == null) {
            response.sendError(
                    HttpServletResponse.SC_NOT_FOUND,
                    "Filme não encontrado."
            );
            return;
        }

        request.setAttribute("filme", filme);

        request.getRequestDispatcher("/detalhes.jsp").forward(request, response);
    }
}