CREATE TABLE carrinhos(
    car_id SERIAL PRIMARY KEY,
    car_cli_id INTEGER NOT NULL,
    car_criado_em TIMESTAMP DEFAULT NOW(),
    CONSTRAINT fk_car_cli FOREIGN KEY ( car_cli_id ) REFERENCES clientes(cli_id)
);

CREATE TABLE itens_carrinho(
   itc_id SERIAL PRIMARY KEY,
   itc_quantidade SMALLINT NOT NULL,
   itc_car_id INTEGER NOT NULL,
   itc_pro_id INTEGER NOT NULL,
   CONSTRAINT fk_itc_car FOREIGN KEY ( itc_car_id ) REFERENCES carrinhos(car_id),
   CONSTRAINT fk_itc_pro FOREIGN KEY ( itc_pro_id ) REFERENCES produtos(pro_id)
);