package org.example.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    private static final String URL = "jdbc:mysql://localhost:3306/catalogo_filmes";
    private static final String USUARIO = "catalogo_app";
    private static final String SENHA =
            System.getenv("CATALOGO_DB_PASSWORD");

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