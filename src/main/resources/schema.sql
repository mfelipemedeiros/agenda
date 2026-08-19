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