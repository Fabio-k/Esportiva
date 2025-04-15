CREATE TABLE notificacoes (
    not_id SERIAL PRIMARY KEY,
    not_mensagem VARCHAR(500) NOT NULL,
    not_criado_em TIMESTAMP NOT NULL,
    not_visto BOOLEAN DEFAULT false NOT NULL,
    not_cli_id INTEGER NOT NULL,
    CONSTRAINT fk_not_cli FOREIGN KEY ( not_cli_id ) REFERENCES clientes(cli_id)
);