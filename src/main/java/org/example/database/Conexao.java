package org.example.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Centraliza a configuração e a abertura de conexões com o MySQL.
 *
 * A conexão utiliza o banco catalogo_filmes e o usuário dedicado
 * catalogo_app. A senha é obtida pela variável de ambiente
 * CATALOGO_DB_PASSWORD, evitando armazenar a credencial no código-fonte.
 */
public class Conexao {

    private static final String URL =
            "jdbc:mysql://localhost:3306/catalogo_filmes";
    private static final String USUARIO = "catalogo_app";
    private static final String SENHA =
            System.getenv("CATALOGO_DB_PASSWORD");

    /**
     * Abre uma conexão JDBC com o banco de dados configurado.
     *
     * Verifica se a variável de ambiente da senha está disponível,
     * carrega o driver JDBC do MySQL e solicita a conexão ao DriverManager.
     *
     * @return conexão aberta com o banco de dados
     * @throws SQLException se a senha não estiver configurada,
     *                      se o driver não for encontrado ou se a conexão falhar
     */
    public static Connection conectar() throws SQLException {

        if (SENHA == null || SENHA.isBlank()) {
            throw new SQLException(
                    "A variável de ambiente CATALOGO_DB_PASSWORD não está definida."
            );
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver JDBC do MySQL não encontrado.", e);
        }

        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }
}