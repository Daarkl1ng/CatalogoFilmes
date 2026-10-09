# Catálogo de Filmes

## 1. Descrição do projeto

O Catálogo de Filmes é uma aplicação web desenvolvida em Java
que permite cadastrar, consultar, editar e excluir informações
sobre filmes. Os dados são armazenados em um banco de dados MySQL.

A aplicação possui uma interface web construída com JSP, enquanto
os Servlets recebem e processam as requisições dos usuários.

## 2. Funcionalidades

- Listagem dos filmes cadastrados.
- Busca por título ou diretor.
- Visualização dos detalhes de um filme.
- Cadastro de novos filmes.
- Edição dos dados de filmes existentes.
- Exclusão de filmes com confirmação.
- Validação dos dados enviados pelos formulários.

## 3. Tecnologias utilizadas

- Java 21
- Apache Maven
- MySQL
- JDBC
- Apache Tomcat 10.1
- Jakarta Servlet API
- JSP
- JSTL
- HTML
- CSS

## 4. Arquitetura da aplicação

### Model

A classe `Filme` representa um filme e seus atributos:
ID, título, diretor, ano de lançamento, gênero e sinopse.

### Database / DAO

A classe `Conexao` estabelece a conexão com o banco de dados.

A classe `FilmeDAO` executa as operações SQL de consulta,
cadastro, atualização, exclusão e busca de filmes.

### Servlet

Os Servlets recebem as requisições HTTP, validam os parâmetros,
chamam os métodos do DAO e encaminham os dados para as páginas JSP.

### View

As páginas JSP apresentam os dados e os formulários utilizados
pelo usuário. A JSTL permite percorrer listas e exibir valores
sem colocar a lógica SQL na interface.

## 5. Banco de dados

Banco: `catalogo_filmes`

Tabela principal: `filmes`

- `id`: identificador do filme.
- `titulo`: título do filme.
- `diretor`: diretor.
- `ano_lancamento`: ano de lançamento.
- `genero`: gênero cinematográfico.
- `sinopse`: descrição do filme.

O ID é gerado automaticamente pelo banco de dados.

### Criar o banco e a tabela

O script `sql/schema.sql` contém a estrutura necessária para criar o banco de dados e a tabela principal.

Para executá-lo usando o MySQL Workbench:

1. Abra o MySQL Workbench e conecte-se ao servidor MySQL.
2. Abra o arquivo `sql/schema.sql`.
3. Execute o script para criar o banco `catalogo_filmes` e a tabela `filmes`.

### Criar o usuário da aplicação

A aplicação utiliza o usuário `catalogo_app`, separado do usuário administrativo `root`.

Em uma instalação nova, execute o comando abaixo no MySQL, substituindo o texto de exemplo por uma senha forte e exclusiva:

```sql
CREATE USER 'catalogo_app'@'localhost'
IDENTIFIED BY 'SUBSTITUA_POR_UMA_SENHA_FORTE';

GRANT SELECT, INSERT, UPDATE, DELETE
ON catalogo_filmes.*
TO 'catalogo_app'@'localhost';
```

A senha escolhida para `catalogo_app` deve ser configurada na variável de ambiente `CATALOGO_DB_PASSWORD`. Não armazene a senha no código-fonte nem a publique no repositório.

O usuário da aplicação possui apenas as permissões necessárias para consultar, cadastrar, atualizar e excluir filmes.

## 6. Segurança e validação

- Consultas parametrizadas com `PreparedStatement`.
- Escape de saída HTML com JSTL `<c:out>`.
- Validação dos campos no servidor.
- Limites de tamanho para os campos de texto.
- Tratamento de erros SQL nos fluxos implementados.
- Credenciais do banco configuradas por variável de ambiente.

A senha do banco é obtida pela variável
`CATALOGO_DB_PASSWORD`, sem ser armazenada no código-fonte.

## 7. Como executar

1. Instalar e iniciar o MySQL Server.
2. Garantir que o banco `catalogo_filmes` e a tabela `filmes`
   existam.
3. Configurar a variável de ambiente `CATALOGO_DB_PASSWORD`
   com a senha do usuário dedicado da aplicação.
4. Configurar Java 21 e as dependências Maven.
5. Executar `clean` e `package` pelo Maven.
6. Copiar o arquivo WAR gerado em `target` para a pasta
   `webapps` do Apache Tomcat 10.1.
7. Iniciar o Tomcat e acessar a aplicação pelo navegador.

## 8. Acesso à aplicação

Endereço local utilizado durante o desenvolvimento:

http://localhost:8080/CatalogoFilmes-1.0-SNAPSHOT/filmes