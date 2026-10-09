package org.example.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.example.model.Filme;
import java.util.ArrayList;
import java.sql.PreparedStatement;

public class FilmeDAO {

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

    public boolean excluirFilme(int id) throws SQLException {
        String sql = "DELETE FROM filmes WHERE id = ?";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement statement = conexao.prepareStatement(sql)) {

            statement.setInt(1, id);

            int linhasExcluidas = statement.executeUpdate();

            return linhasExcluidas > 0;
        }
    }

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