-- Geração de Modelo físico
-- Sql ANSI 2003 - brModelo.



CREATE TABLE CLIENTE (
cli_genero VARCHAR(10),
cli_nome VARCHAR(30),
cli_status VARCHAR(10),
cli_cpf VARCHAR(11),
cli_data_nascimento DATETIME,
cli_id INTEGER PRIMARY KEY
)

CREATE TABLE GRUPO DE PRECIFICAÇÃO (
grp_id VARCHAR(10) PRIMARY KEY,
grp_valor_precificacao NUMERIC(10),
grp_nome VARCHAR(10)
)

CREATE TABLE PRODUTO (
pro_id INTEGER PRIMARY KEY,
pro_quantidade_estoque INTEGER,
pro_quantidade_bloqueada INTEGER,
pro_valor_precificacao NUMERIC(10),
pro_justificativa_inativacao VARCHAR(50),
pro_categoria_inativacao VARCHAR(20),
pro_valor_venda NUMERIC(10),
pro_valor_custo NUMERIC(10),
pro_data_entrada DATETIME,
pro_nome_produto VARCHAR(30)
)

CREATE TABLE TRANSAÇÃO (
tra_data_compra DATETIME,
tra_id INTEGER,
cli_id INTEGER,
PRIMARY KEY(tra_id,cli_id),
FOREIGN KEY(cli_id) REFERENCES CLIENTE (cli_id)
)

CREATE TABLE LOG (
log_id INTEGER PRIMARY KEY,
log_usuario VARCHAR(30),
log_operacao VARCHAR(10),
log_conteudo_alteracao VARCHAR(30),
log_data_hora DATETIME
)

CREATE TABLE PEDIDO (
ped_status VARCHAR(10),
ped_quantidade INTEGER,
tra_id INTEGER,
pro_id INTEGER,
PRIMARY KEY(tra_id,pro_id),
FOREIGN KEY(/*erro: ??*/) REFERENCES TRANSAÇÃO (tra_id,cli_id),
FOREIGN KEY(pro_id) REFERENCES PRODUTO (pro_id)
)

CREATE TABLE ENDEREÇO (
end_logradouro VARCHAR(20),
end_cep INTEGER,
end_numero INTEGER,
end_bairro VARCHAR(20),
end_id INTEGER PRIMARY KEY,
end_tipo_residencia VARCHAR(20),
end_numero1 INTEGER,
end_observacao VARCHAR(50),
end_tipo_logradouro VARCHAR(20),
end_categoria_endereco VARCHAR(10),
end_pais VARCHAR(20),
end_cidade VARCHAR(20),
end_estado VARCHAR(20)
)

CREATE TABLE CARTÃO DE CRÉDITO (
car_numero_cartao INTEGER,
car_nome_impresso_cartao VARCHAR(30),
car_codigo_seguranca INTEGER,
car_bandeira_cartao VARCHAR(10),
PRIMARY KEY(car_numero_cartao,car_bandeira_cartao)
)

CREATE TABLE cli_telefone (
cli_telefone_PK INTEGER PRIMARY KEY,
cli_telefone INTEGER
)

CREATE TABLE cli_email (
cli_email_PK INTEGER PRIMARY KEY,
cli_email VARCHAR(30)
)

CREATE TABLE cli_cupom_troca (
cli_cupom_troca_PK INTEGER PRIMARY KEY,
cli_cupom_troca INTEGER
)

CREATE TABLE cli_cupom_promocao (
cli_cupom_promocao_PK INTEGER,
cli_cupom_promocao NUMERIC(10),
cli_id_FK INTEGER,
PRIMARY KEY(cli_cupom_promocao_PK,cli_id_FK),
FOREIGN KEY(cli_id_FK) REFERENCES CLIENTE (cli_id)
)

CREATE TABLE pro_categoria_produto (
pro_categoria_produto_PK INTEGER,
pro_categoria_produto VARCHAR(20),
pro_id_FK INTEGER,
PRIMARY KEY(pro_categoria_produto_PK,pro_id_FK),
FOREIGN KEY(pro_id_FK) REFERENCES PRODUTO (pro_id)
)

CREATE TABLE PAGAMENTO (
car_numero_cartao INTEGER,
car_bandeira_cartao VARCHAR(10),
cli_id INTEGER,
PRIMARY KEY(car_numero_cartao,car_bandeira_cartao,cli_id),
FOREIGN KEY(car_bandeira_cartao,,) REFERENCES CARTÃO DE CRÉDITO (car_numero_cartao,car_bandeira_cartao),
FOREIGN KEY(cli_id) REFERENCES CLIENTE (cli_id)
)

CREATE TABLE PERTENCE (
grp_id VARCHAR(10),
pro_id INTEGER,
PRIMARY KEY(grp_id,pro_id),
FOREIGN KEY(grp_id) REFERENCES GRUPO DE PRECIFICAÇÃO (grp_id),
FOREIGN KEY(pro_id) REFERENCES PRODUTO (pro_id)
)

CREATE TABLE RECEBE (
cli_id INTEGER,
end_id INTEGER,
PRIMARY KEY(cli_id,end_id),
FOREIGN KEY(cli_id) REFERENCES CLIENTE (cli_id),
FOREIGN KEY(end_id) REFERENCES ENDEREÇO (end_id)
)

