package org.example.model;

/**
 * Representa um filme cadastrado no catálogo.
 *
 * Armazena os dados principais de um filme, incluindo título,
 * diretor, ano de lançamento, gênero e sinopse.
 */
public class Filme {

    private int id;
    private String titulo;
    private String diretor;
    private int anoLancamento;
    private String genero;
    private String sinopse;

    /**
     * Cria um objeto Filme com os dados informados.
     *
     * @param id identificador do filme
     * @param titulo título do filme
     * @param diretor diretor do filme
     * @param anoLancamento ano de lançamento do filme
     * @param genero gênero cinematográfico
     * @param sinopse descrição resumida do filme
     */
    public Filme(int id, String titulo, String diretor,
                 int anoLancamento, String genero, String sinopse) {
        this.id = id;
        this.titulo = titulo;
        this.diretor = diretor;
        this.anoLancamento = anoLancamento;
        this.genero = genero;
        this.sinopse = sinopse;
    }

    /**
     * Retorna o identificador do filme.
     *
     * @return identificador do filme
     */
    public int getId() {
        return id;
    }

    /**
     * Define o identificador do filme.
     *
     * @param id novo identificador
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retorna o título do filme.
     *
     * @return título do filme
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Define o título do filme.
     *
     * @param titulo novo título
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * Retorna o diretor do filme.
     *
     * @return diretor do filme
     */
    public String getDiretor() {
        return diretor;
    }

    /**
     * Define o diretor do filme.
     *
     * @param diretor novo diretor
     */
    public void setDiretor(String diretor) {
        this.diretor = diretor;
    }

    /**
     * Retorna o ano de lançamento do filme.
     *
     * @return ano de lançamento
     */
    public int getAnoLancamento() {
        return anoLancamento;
    }

    /**
     * Define o ano de lançamento do filme.
     *
     * @param anoLancamento novo ano de lançamento
     */
    public void setAnoLancamento(int anoLancamento) {
        this.anoLancamento = anoLancamento;
    }

    /**
     * Retorna o gênero do filme.
     *
     * @return gênero cinematográfico
     */
    public String getGenero() {
        return genero;
    }

    /**
     * Define o gênero do filme.
     *
     * @param genero novo gênero
     */
    public void setGenero(String genero) {
        this.genero = genero;
    }

    /**
     * Retorna a sinopse do filme.
     *
     * @return sinopse do filme
     */
    public String getSinopse() {
        return sinopse;
    }

    /**
     * Define a sinopse do filme.
     *
     * @param sinopse nova sinopse
     */
    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    /**
     * Retorna uma representação textual dos dados do filme.
     *
     * @return texto contendo os atributos do filme
     */
    @Override
    public String toString() {
        return "Filme{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", diretor='" + diretor + '\'' +
                ", anoLancamento=" + anoLancamento +
                ", genero='" + genero + '\'' +
                ", sinopse='" + sinopse + '\'' +
                '}';
    }
}