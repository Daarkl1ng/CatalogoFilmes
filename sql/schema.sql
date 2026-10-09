CREATE DATABASE IF NOT EXISTS catalogo_filmes
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE catalogo_filmes;

CREATE TABLE IF NOT EXISTS filmes (
    id INT NOT NULL AUTO_INCREMENT,
    titulo VARCHAR(255) NOT NULL,
    diretor VARCHAR(255) NOT NULL,
    ano_lancamento INT NOT NULL,
    genero VARCHAR(100) NOT NULL,
    sinopse TEXT NULL,

    PRIMARY KEY (id)
  ) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_unicode_ci;