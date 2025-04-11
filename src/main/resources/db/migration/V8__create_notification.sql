CREATE TABLE notificacoes (
    not_id SERIAL PRIMARY KEY,
    not_mensagem VARCHAR(500) NOT NULL,
    not_created_at TIMESTAMP NOT NULL,
    not_cli_id INTEGER NOT NULL,
    CONSTRAINT fk_not_cli FOREIGN KEY ( not_cli_id ) REFERENCES clientes(cli_id)
);