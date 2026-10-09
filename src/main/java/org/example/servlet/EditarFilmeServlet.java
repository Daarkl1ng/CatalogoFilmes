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
 * Controla a consulta e a atualização dos dados de filmes.
 *
 * Disponibiliza o formulário de edição preenchido com os dados
 * existentes e processa as alterações enviadas pelo usuário.
 */

@WebServlet("/filmes/editar")
public class EditarFilmeServlet extends HttpServlet {

    /**
     * Carrega os dados de um filme para o formulário de edição.
     *
     * Obtém o identificador da requisição, consulta o registro no banco
     * e encaminha o objeto Filme para editar.jsp.
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
            log("Erro ao buscar filme para edição.", e);

            response.sendError(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Não foi possível carregar o formulário de edição. Tente novamente."
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

        request.getRequestDispatcher("/editar.jsp").forward(request, response);
    }

    /**
     * Valida e salva as alterações de um filme existente.
     *
     * Verifica os campos obrigatórios, os limites de tamanho, o identificador
     * e o ano de lançamento. Em seguida, solicita a atualização ao FilmeDAO.
     * Quando a operação é concluída, redireciona ao catálogo. Se o filme não
     * existir ou ocorrer uma falha SQL, envia a resposta HTTP correspondente.
     *
     * @param request requisição HTTP contendo os dados editados
     * @param response resposta HTTP a ser enviada
     * @throws ServletException se ocorrer uma falha no processamento do Servlet
     * @throws IOException se ocorrer uma falha de entrada ou saída
     */

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String idTexto = request.getParameter("id");
        String titulo = request.getParameter("titulo");
        String diretor = request.getParameter("diretor");
        String anoTexto = request.getParameter("anoLancamento");
        String genero = request.getParameter("genero");
        String sinopse = request.getParameter("sinopse");

        if (idTexto == null || idTexto.isBlank()
                || titulo == null || titulo.isBlank()
                || diretor == null || diretor.isBlank()
                || anoTexto == null || anoTexto.isBlank()
                || genero == null || genero.isBlank()) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "ID, título, diretor, ano e gênero são obrigatórios."
            );
            return;
        }

        if (titulo.trim().length() > 255
                || diretor.trim().length() > 255
                || genero.trim().length() > 100
                || (sinopse != null && sinopse.length() > 5000)) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Um ou mais campos excedem o tamanho permitido."
            );
            return;
        }

        int id;
        int anoLancamento;

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

        try {
            anoLancamento = Integer.parseInt(anoTexto);
        } catch (NumberFormatException e) {
            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "O ano de lançamento precisa ser um número válido."
            );
            return;
        }

        if (anoLancamento < 1800 || anoLancamento > 2100) {
            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "O ano precisa estar entre 1800 e 2100."
            );
            return;
        }

        Filme filme = new Filme(
                id,
                titulo.trim(),
                diretor.trim(),
                anoLancamento,
                genero.trim(),
                sinopse == null ? "" : sinopse.trim()
        );

        FilmeDAO filmeDAO = new FilmeDAO();

        try {
            boolean atualizado = filmeDAO.atualizarFilme(filme);

            if (atualizado) {
                response.sendRedirect(request.getContextPath() + "/filmes");
            } else {
                response.sendError(
                        HttpServletResponse.SC_NOT_FOUND,
                        "Filme não encontrado para atualização."
                );
            }

        } catch (SQLException e) {
            log("Erro ao atualizar filme no banco de dados.", e);

            response.sendError(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Não foi possível atualizar o filme. Tente novamente."
            );
        }
    }

}