package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.example.database.FilmeDAO;
import org.example.model.Filme;
import java.util.ArrayList;
import java.sql.SQLException;

/**
 * Controla a listagem e a busca de filmes do catálogo.
 *
 * Recupera os registros por meio do FilmeDAO e encaminha os resultados
 * para a página principal index.jsp. Quando ocorre uma falha SQL,
 * registra o erro no log do servidor e retorna uma resposta HTTP 500.
 */

@WebServlet("/filmes")
public class FilmeServlet extends HttpServlet {

    /**
     * Processa a solicitação de listagem ou busca de filmes.
     *
     * Se o parâmetro "busca" estiver preenchido, pesquisa por título
     * ou diretor. Caso contrário, recupera todos os filmes cadastrados.
     *
     * @param request requisição HTTP recebida
     * @param response resposta HTTP a ser enviada
     * @throws ServletException se ocorrer uma falha no processamento do Servlet
     * @throws IOException se ocorrer uma falha de entrada ou saída
     */

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            FilmeDAO filmeDAO = new FilmeDAO();

            String termoBusca = request.getParameter("busca");
            ArrayList<Filme> filmes;

            if (termoBusca != null && !termoBusca.isBlank()) {
                filmes = filmeDAO.buscar(termoBusca.trim());
            } else {
                filmes = filmeDAO.listarFilmes();
            }

            request.setAttribute("filmes", filmes);
            request.getRequestDispatcher("/index.jsp").forward(request, response);

        } catch (SQLException e) {
            log("Erro ao consultar o catálogo de filmes.", e);

            response.sendError(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Não foi possível carregar o catálogo. Tente novamente."
            );
        }
    }
}