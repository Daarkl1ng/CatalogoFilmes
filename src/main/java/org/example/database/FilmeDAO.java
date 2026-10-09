package org.example.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.example.model.Filme;

/**
 * Responsável pelo acesso aos dados dos filmes no banco de dados.
 *
 * Encapsula as operações SQL de listagem, consulta por identificador,
 * cadastro, atualização, exclusão e busca por título ou diretor.
 * Utiliza JDBC para executar as operações no MySQL.
 */
public class FilmeDAO {

    /**
     * Retorna todos os filmes cadastrados no banco de dados.
     *
     * @return lista contendo os filmes encontrados; vazia se não houver registros
     * @throws SQLException se ocorrer uma falha durante a consulta ao banco
     */
    public ArrayList<Filme> listarFilmes() throws SQLException {
        String sql = "SELECT * FROM filmes";
        ArrayList<Filme> filmes = new ArrayList<>();

        try (Connection conexao = Conexao.conectar();
             Statement statement = conexao.createStatement();
             ResultSet resultado = statement.executeQuery(sql)) {

            while (resultado.next()) {
                Filme filme = new Filme(
                        resultado.getInt("id"),
                        resultado.getString("titulo"),
                        resultado.getString("diretor"),
                        resultado.getInt("ano_lancamento"),
                        resultado.getString("genero"),
                        resultado.getString("sinopse")
                );

                filmes.add(filme);
            }
        }

        return filmes;
    }

    /**
     * Consulta um filme pelo seu identificador.
     *
     * @param id identificador do filme procurado
     * @return o filme encontrado ou null se não existir registro com esse ID
     * @throws SQLException se ocorrer uma falha durante a consulta ao banco
     */
    public Filme buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM filmes WHERE id = ?";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement statement = conexao.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet resultado = statement.executeQuery()) {
                if (resultado.next()) {
                    return new Filme(
                            resultado.getInt("id"),
                            resultado.getString("titulo"),
                            resultado.getString("diretor"),
                            resultado.getInt("ano_lancamento"),
                            resultado.getString("genero"),
                            resultado.getString("sinopse")
                    );
                }
            }
        }

        return null;
    }

    /**
     * Insere um novo filme no banco de dados.
     *
     * @param filme objeto contendo os dados do filme a ser cadastrado
     * @return true se pelo menos uma linha foi inserida; false caso contrário
     * @throws SQLException se ocorrer uma falha durante a inserção
     */
    public boolean adicionarFilme(Filme filme) throws SQLException {
        String sql = "INSERT INTO filmes (titulo, diretor, ano_lancamento, genero, sinopse) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement statement = conexao.prepareStatement(sql)) {

            statement.setString(1, filme.getTitulo());
            statement.setString(2, filme.getDiretor());
            statement.setInt(3, filme.getAnoLancamento());
            statement.setString(4, filme.getGenero());
            statement.setString(5, filme.getSinopse());

            int linhasInseridas = statement.executeUpdate();

            if (linhasInseridas > 0) {
                return true;
            }

            return false;
        }
    }

    /**
     * Atualiza os dados de um filme existente.
     *
     * Se a instrução UPDATE não alterar nenhuma linha, verifica se o
     * identificador ainda existe. Isso permite considerar bem-sucedida
     * uma edição em que os dados já eram iguais aos valores enviados.
     *
     * @param filme objeto contendo o identificador e os novos dados
     * @return true se o filme existir e a operação for considerada bem-sucedida;
     *         false se não houver filme com o identificador informado
     * @throws SQLException se ocorrer uma falha durante a atualização ou verificação
     */
    public boolean atualizarFilme(Filme filme) throws SQLException {
        String sql = "UPDATE filmes SET titulo = ?, diretor = ?, "
                + "ano_lancamento = ?, genero = ?, sinopse = ? "
                + "WHERE id = ?";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement statement = conexao.prepareStatement(sql)) {

            statement.setString(1, filme.getTitulo());
            statement.setString(2, filme.getDiretor());
            statement.setInt(3, filme.getAnoLancamento());
            statement.setString(4, filme.getGenero());
            statement.setString(5, filme.getSinopse());
            statement.setInt(6, filme.getId());

            int linhasAtualizadas = statement.executeUpdate();

            if (linhasAtualizadas > 0) {
                return true;
            }

            // Se nenhuma linha mudou, verificamos se o filme ainda existe.
            String sqlVerificar = "SELECT id FROM filmes WHERE id = ?";

            try (PreparedStatement verificacao =
                         conexao.prepareStatement(sqlVerificar)) {

                verificacao.setInt(1, filme.getId());

                try (ResultSet resultado = verificacao.executeQuery()) {
                    return resultado.next();
                }
            }
        }
    }

    /**
     * Exclui um filme utilizando seu identificador.
     *
     * @param id identificador do filme a ser excluído
     * @return true se um registro foi excluído; false se nenhum registro
     *         correspondente ao ID foi encontrado
     * @throws SQLException se ocorrer uma falha durante a exclusão
     */
    public boolean excluirFilme(int id) throws SQLException {
        String sql = "DELETE FROM filmes WHERE id = ?";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement statement = conexao.prepareStatement(sql)) {

            statement.setInt(1, id);

            int linhasExcluidas = statement.executeUpdate();

            return linhasExcluidas > 0;
        }
    }

    /**
     * Pesquisa filmes por correspondência parcial no título ou no diretor.
     *
     * A consulta utiliza PreparedStatement para manter o termo pesquisado
     * separado da instrução SQL.
     *
     * @param termo texto utilizado na busca
     * @return lista dos filmes correspondentes; vazia se não houver resultados
     * @throws SQLException se ocorrer uma falha durante a consulta
     */
    public ArrayList<Filme> buscar(String termo) throws SQLException {
        String sql = "SELECT * FROM filmes WHERE titulo LIKE ? OR diretor LIKE ?";
        ArrayList<Filme> filmes = new ArrayList<>();

        try (Connection conexao = Conexao.conectar();
             PreparedStatement statement = conexao.prepareStatement(sql)) {

            statement.setString(1, "%" + termo + "%");
            statement.setString(2, "%" + termo + "%");

            try (ResultSet resultado = statement.executeQuery()) {
                while (resultado.next()) {
                    Filme filme = new Filme(
                            resultado.getInt("id"),
                            resultado.getString("titulo"),
                            resultado.getString("diretor"),
                            resultado.getInt("ano_lancamento"),
                            resultado.getString("genero"),
                            resultado.getString("sinopse")
                    );

                    filmes.add(filme);
                }
            }
        }

        return filmes;
    }
}