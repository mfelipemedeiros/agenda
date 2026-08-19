# Agenda Telefônica WEB

Aplicação WEB simples para cadastro, pesquisa, alteração e exclusão de contatos de uma agenda telefônica.

O sistema permite cadastrar uma pessoa com **nome**, **idade** e um ou mais **telefones**. Também é possível pesquisar contatos pelo **nome** ou pelo **número do telefone**.

Ao excluir um contato, o sistema grava um **LOG em arquivo texto** informando os dados do contato excluído.

---

## Tecnologias utilizadas

- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA
- Thymeleaf
- PostgreSQL
- HTML
- CSS
- Maven

---

## Funcionalidades

- Cadastrar contatos
- Adicionar vários telefones para um contato
- Pesquisar contatos por nome
- Pesquisar contatos por telefone
- Alterar contato selecionado
- Excluir contato selecionado
- Registrar exclusões em arquivo de LOG

---

## Estrutura das tabelas

### Tabela: `contato`

| Campo | Tipo | Observação |
|---|---|---|
| id | BIGSERIAL | PK |
| nome | VARCHAR(100) | Nome do contato |
| idade | NUMERIC(3) | Idade do contato |

### Tabela: `telefone`

| Campo | Tipo | Observação |
|---|---|---|
| idcontato | BIGINT | PK / FK |
| id | BIGSERIAL | PK |
| numero | VARCHAR(16) | Número do telefone |

---

## Banco de dados

O projeto utiliza PostgreSQL.

Crie o banco de dados com o comando:

```sql
CREATE DATABASE agendaTelefonica;
```

Depois execute o script abaixo:

```sql
DROP TABLE IF EXISTS telefone;
DROP TABLE IF EXISTS contato;

CREATE TABLE contato (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    idade NUMERIC(3) NOT NULL
);

CREATE TABLE telefone (
    idcontato BIGINT NOT NULL,
    id BIGSERIAL NOT NULL,
    numero VARCHAR(16) NOT NULL,

    CONSTRAINT pk_telefone PRIMARY KEY (idcontato, id),

    CONSTRAINT fk_telefone_contato
        FOREIGN KEY (idcontato)
        REFERENCES contato(id)
        ON DELETE CASCADE
);

INSERT INTO contato (nome, idade) VALUES
('João Silva', 30),
('Maria Oliveira', 25),
('Carlos Souza', 42);

INSERT INTO telefone (idcontato, numero) VALUES
(1, '(11) 99999-1111'),
(1, '(11) 3333-2222'),
(2, '(21) 98888-3333'),
(3, '(31) 97777-4444');
```

---

## Configuração

No arquivo:

```text
src/main/resources/application.properties
```

Configure o acesso ao PostgreSQL:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/agendaTelefonica
spring.datasource.username=agendauser
spring.datasource.password=1234

spring.jpa.hibernate.ddl-auto=none
spring.sql.init.mode=always
server.port=8080
```

Caso seu usuário ou senha sejam diferentes, altere as propriedades:

```properties
spring.datasource.username
spring.datasource.password
```

---

## Como executar

Clone o repositório:

```bash
git clone https://github.com/mfelipemedeiros/agenda.git
```

Acesse a pasta do projeto:

```bash
cd agenda
```

Execute a aplicação com Maven:

```bash
mvn spring-boot:run
```

Acesse no navegador:

```text
http://localhost:8080
```

---

## LOG de exclusão

Sempre que um contato for excluído, será criado ou atualizado o arquivo:

```text
log_exclusao_contatos.txt
```

Exemplo de LOG:

```text
[19/08/2026 14:32:10] Contato excluído - ID: 1 | Nome: João Silva | Idade: 30 | Telefones: (11) 99999-1111, (11) 3333-2222
```

---

## Telas do sistema

- Tela de pesquisa de contatos
- Tela de cadastro de contatos
- Tela de alteração de contatos
