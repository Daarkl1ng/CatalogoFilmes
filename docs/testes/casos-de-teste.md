# Registro de Testes — Catálogo de Filmes

## 1. Objetivo

Registrar os testes manuais funcionais e de segurança realizados
durante o desenvolvimento da aplicação, verificando o comportamento
da interface web, a persistência dos dados e as validações do sistema.

## 2. Testes funcionais

| ID | Teste | Resultado observado | Situação |
|---|---|---|---|
| TF01 | Listar filmes | O catálogo exibiu os filmes cadastrados no MySQL. | Aprovado |
| TF02 | Cadastrar filme | O filme de teste foi inserido e apareceu na listagem. | Aprovado |
| TF03 | Visualizar detalhes | A página exibiu corretamente os dados do filme selecionado. | Aprovado |
| TF04 | Editar filme | A alteração foi salva e permaneceu após reabrir o formulário. | Aprovado |
| TF05 | Excluir filme | O registro de teste foi removido e a operação foi confirmada pelo servidor. | Aprovado |
| TF06 | Buscar por título | A busca parcial por "Anéis" encontrou O Senhor dos Anéis. | Aprovado |
| TF07 | Buscar por diretor | A busca por "Chris" encontrou Interestelar. | Aprovado |
| TF08 | Busca vazia | Todos os filmes cadastrados voltaram a ser exibidos. | Aprovado |
| TF09 | Busca sem resultados | A busca por "Batman" exibiu a mensagem de ausência de correspondências. | Aprovado |
| TF10 | Abrir formulário de edição | Os campos foram preenchidos com os dados existentes do filme. | Aprovado |
| TF11 | Validar ano de lançamento | O ano 1700 foi impedido pela validação da aplicação. | Aprovado |
| TF12 | Validar tamanho do título | Após contornar o limite do navegador, um título acima de 255 caracteres foi rejeitado pelo servidor com HTTP 400. | Aprovado |

## 3. Testes de segurança

| ID | Teste | Resultado observado | Situação |
|---|---|---|---|
| TS01 | Verificação básica de XSS | O texto `<script>alert('XSS')</script>` foi exibido literalmente, sem abrir um alerta JavaScript. O registro temporário foi excluído depois. | Aprovado |
| TS02 | Verificação básica de SQL Injection | A busca por `' OR 1=1 --` não retornou todos os registros; o texto foi tratado como termo de pesquisa. | Aprovado |
| TS03 | Credenciais do banco | A aplicação funcionou com a senha lida da variável de ambiente `CATALOGO_DB_PASSWORD`. | Aprovado |
| TS04 | Privilégios do banco | O usuário `catalogo_app` foi configurado com permissões de SELECT, INSERT, UPDATE e DELETE no banco da aplicação. | Aprovado |

## 4. Testes de compilação e integração

O projeto foi compilado com Maven utilizando as etapas `clean` e `package`, com resultado BUILD SUCCESS.

Após a publicação do WAR no Apache Tomcat, as funcionalidades principais foram testadas novamente pelo navegador e permaneceram operacionais.

## 5. Observações

Os testes foram executados manualmente durante o desenvolvimento.

Os testes de XSS e SQL Injection verificam cenários específicos e não representam uma auditoria completa de segurança.

Não foram registrados testes unitários automatizados com JUnit nesta etapa.