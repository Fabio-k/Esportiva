CREATE TABLE carrinhos(
    crr_id SERIAL PRIMARY KEY,
    crr_cli_id INTEGER NOT NULL,
    crr_criado_em TIMESTAMP DEFAULT NOW(),
    CONSTRAINT fk_crr_cli FOREIGN KEY ( crr_cli_id ) REFERENCES clientes(cli_id)
);

CREATE TABLE itens_carrinho(
   itc_id SERIAL PRIMARY KEY,
   itc_quantidade SMALLINT NOT NULL,
   itc_crr_id INTEGER NOT NULL,
   itc_pro_id INTEGER NOT NULL,
   itc_pro_data_inclusao TIMESTAMP DEFAULT NOW(),
   CONSTRAINT fk_itc_crr FOREIGN KEY ( itc_crr_id ) REFERENCES carrinhos(crr_id),
   CONSTRAINT fk_itc_pro FOREIGN KEY ( itc_pro_id ) REFERENCES produtos(pro_id)
);