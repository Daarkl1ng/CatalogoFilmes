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

@WebServlet("/filmes/detalhes")
public class DetalhesFilmeServlet extends HttpServlet {

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