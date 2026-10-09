package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.example.database.FilmeDAO;
import org.example.model.Filme;
import java.sql.SQLException;

/**
 * Controla o cadastro de novos filmes no catálogo.
 *
 * Disponibiliza o formulário de cadastro e processa os dados enviados
 * pelo usuário, validando as entradas antes de solicitar a inserção
 * do registro ao FilmeDAO.
 */

@WebServlet("/filmes/novo")
public class CadastroFilmeServlet extends HttpServlet {

    /**
     * Exibe o formulário de cadastro de filmes.
     *
     * @param request requisição HTTP recebida
     * @param response resposta HTTP a ser enviada
     * @throws ServletException se ocorrer uma falha no processamento do Servlet
     * @throws IOException se ocorrer uma falha de entrada ou saída
     */

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/cadastro.jsp").forward(request, response);
    }

    /**
     * Valida os dados enviados pelo formulário e solicita o cadastro do filme.
     *
     * Verifica os campos obrigatórios, os limites de tamanho e o ano
     * de lançamento. Se a inserção for concluída, redireciona ao catálogo;
     * caso contrário, registra a falha SQL, quando aplicável, e retorna
     * uma resposta HTTP de erro.
     *
     * @param request requisição HTTP contendo os dados do formulário
     * @param response resposta HTTP a ser enviada
     * @throws ServletException se ocorrer uma falha no processamento do Servlet
     * @throws IOException se ocorrer uma falha de entrada ou saída
     */

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String titulo = request.getParameter("titulo");
        String diretor = request.getParameter("diretor");
        String anoTexto = request.getParameter("anoLancamento");
        String genero = request.getParameter("genero");
        String sinopse = request.getParameter("sinopse");

        if (titulo == null || titulo.isBlank()
                || diretor == null || diretor.isBlank()
                || anoTexto == null || anoTexto.isBlank()
                || genero == null || genero.isBlank()) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Título, diretor, ano e gênero são obrigatórios."
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

        int anoLancamento;

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

        Filme novoFilme = new Filme(
                0,
                titulo.trim(),
                diretor.trim(),
                anoLancamento,
                genero.trim(),
                sinopse == null ? "" : sinopse.trim()
        );

        FilmeDAO filmeDAO = new FilmeDAO();

        try {
            boolean cadastrado = filmeDAO.adicionarFilme(novoFilme);

            if (cadastrado) {
                response.sendRedirect(request.getContextPath() + "/filmes");
            } else {
                response.sendError(
                        HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                        "Não foi possível cadastrar o filme. Tente novamente."
                );
            }

        } catch (SQLException e) {
            log("Erro ao cadastrar filme no banco de dados.", e);

            response.sendError(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Não foi possível cadastrar o filme. Tente novamente."
            );
        }
    }

}