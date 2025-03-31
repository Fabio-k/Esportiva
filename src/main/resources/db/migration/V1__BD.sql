-- Gerado por Oracle SQL Developer Data Modeler 20.2.0.167.1538
--   em:        2025-03-03 09:37:43 BRT
--   site:      Oracle Database 11g
--   tipo:      Oracle Database 11g



-- predefined type, no DDL - MDSYS.SDO_GEOMETRY

-- predefined type, no DDL - XMLTYPE

CREATE TABLE administrador (
    adm_id    SERIAL PRIMARY KEY,
    adm_nome  VARCHAR(30) NOT NULL,
    adm_email VARCHAR(30) NOT NULL
);


CREATE TABLE cartoes_de_credito (
    car_id                SERIAL PRIMARY KEY,
    car_numero            VARCHAR(19) NOT NULL,
    car_bandeira          VARCHAR(20) NOT NULL,
    car_nome_impresso     VARCHAR(30) NOT NULL,
    car_codigo_seguranca  VARCHAR(4) NOT NULL,
    car_cli_id            INTEGER NOT NULL
);


CREATE TABLE categorias_end (
    cae_id SERIAL PRIMARY KEY,
    cae_nome VARCHAR(20) NOT NULL
);


CREATE TABLE categorias_produto (
    cat_id    SERIAL PRIMARY KEY,
    cat_nome  VARCHAR(20) NOT NULL
);


CREATE TABLE cep (
    cep_id          SERIAL PRIMARY KEY,
    cep_cep         VARCHAR(8) NOT NULL,
    cep_logradouro  VARCHAR(50) NOT NULL,
    cep_bairro      VARCHAR(50) NOT NULL,
    cep_cidade      VARCHAR(50) NOT NULL,
    cep_estado      VARCHAR(50) NOT NULL,
    cep_pais        VARCHAR(50) NOT NULL
);


CREATE TABLE clientes (
    cli_id                      SERIAL PRIMARY KEY,
    cli_nome                    VARCHAR(50) NOT NULL,
    cli_cpf                     VARCHAR(11) NOT NULL,
    cli_data_nascimento         DATE NOT NULL,
    cli_genero                  VARCHAR(20) NOT NULL,
    cli_status                  VARCHAR(10) NOT NULL,
    cli_telefone                VARCHAR(13) NOT NULL,
    cli_telefone_tipo           VARCHAR(20) NOT NULL,
    cli_email                   VARCHAR(50) NOT NULL,
    cli_endereco_preferencial   INTEGER NOT NULL,
    cli_cartao_preferencial     INTEGER NOT NULL
);


CREATE TABLE cupons_promocao (
    cpr_id                    SERIAL PRIMARY KEY,
    cpr_desconto_porcentagem  NUMERIC(10,2) NOT NULL,
    cpr_cli_id                INTEGER NOT NULL
);


CREATE TABLE cupons_troca (
    ctr_id          SERIAL PRIMARY KEY,
    ctr_valor       NUMERIC(10,2) NOT NULL,
    ctr_cli_id      INTEGER NOT NULL
);


CREATE TABLE enderecos (
    end_id                   SERIAL PRIMARY KEY,
    end_numero               VARCHAR(10) NOT NULL,
    end_tipo_residencia      VARCHAR(20) NOT NULL,
    end_tipo_logradouro      VARCHAR(20) NOT NULL,
    end_frase_identificacao  VARCHAR(40) NOT NULL,
    end_observacao           VARCHAR(50),
    end_cli_id               INTEGER NOT NULL,
    end_cep_id               INTEGER NOT NULL
);


CREATE TABLE funcao (
    fun_end_id  INTEGER NOT NULL,
    fun_cae_id  INTEGER NOT NULL
);

ALTER TABLE funcao ADD CONSTRAINT funcao_pk PRIMARY KEY ( fun_end_id,
                                                          fun_cae_id );

CREATE TABLE grupo_precificacao (
    grp_id            SERIAL PRIMARY KEY,
    grp_nome          VARCHAR(20) NOT NULL,
    grp_margem_lucro  NUMERIC(10,2) NOT NULL  
);

CREATE TABLE logs (
    log_id                  SERIAL PRIMARY KEY,
    log_usuario             VARCHAR(10) NOT NULL,
    log_data_hora           TIMESTAMP NOT NULL,
    log_operacao            VARCHAR(10) NOT NULL,
    log_conteudo_alteracao  VARCHAR(500) NOT NULL
);

CREATE TABLE pedidos (
    ped_id          SERIAL PRIMARY KEY,
    ped_status      VARCHAR(20) NOT NULL,
    ped_quantidade  INTEGER NOT NULL,
    ped_tra_id      INTEGER NOT NULL,
    ped_pro_id      INTEGER
);

CREATE TABLE pertence (
    per_pro_id  INTEGER NOT NULL,
    per_cat_id  INTEGER NOT NULL
);

ALTER TABLE pertence ADD CONSTRAINT pertence_pk PRIMARY KEY ( per_pro_id,
                                                              per_cat_id );

CREATE TABLE produtos (
    pro_id                        SERIAL PRIMARY KEY,
    pro_nome_produto              VARCHAR(70) NOT NULL,
    pro_data_entrada              DATE NOT NULL,
    pro_quantidade_estoque        INTEGER NOT NULL,
    pro_quantidade_bloqueada      INTEGER NOT NULL,
    pro_margem_lucro              NUMERIC(10,2) NOT NULL,
    pro_valor_custo               NUMERIC(10,2) NOT NULL,
    pro_categoria_inativacao      VARCHAR(23) NOT NULL,
    pro_justificativa_inativacao  VARCHAR(50) NOT NULL,
    pro_descricao                 VARCHAR(500) NOT NULL,
    pro_imagem                    VARCHAR(400) NOT NULL,
    pro_grp_id                    INTEGER
);


CREATE TABLE transacoes (
    tra_id           SERIAL PRIMARY KEY,
    tra_data_compra  DATE NOT NULL,
    tra_status       VARCHAR(20) NOT NULL,
    tra_cli_id       INTEGER NOT NULL
);


ALTER TABLE cartoes_de_credito
    ADD CONSTRAINT cartoes_de_credito_clientes_fk FOREIGN KEY ( car_cli_id )
        REFERENCES clientes ( cli_id );

ALTER TABLE cupons_promocao
    ADD CONSTRAINT cupons_promocao_clientes_fk FOREIGN KEY ( cpr_cli_id )
        REFERENCES clientes ( cli_id );

ALTER TABLE cupons_troca
    ADD CONSTRAINT cupons_troca_clientes_fk FOREIGN KEY ( ctr_cli_id )
        REFERENCES clientes ( cli_id );

ALTER TABLE enderecos
    ADD CONSTRAINT enderecos_cep_fk FOREIGN KEY ( end_cep_id )
        REFERENCES cep ( cep_id );

ALTER TABLE enderecos
    ADD CONSTRAINT enderecos_clientes_fk FOREIGN KEY ( end_cli_id )
        REFERENCES clientes ( cli_id );

ALTER TABLE funcao
    ADD CONSTRAINT funcao_categorias_end_fk FOREIGN KEY ( fun_cae_id )
        REFERENCES categorias_end ( cae_id );

ALTER TABLE funcao
    ADD CONSTRAINT funcao_enderecos_fk FOREIGN KEY ( fun_end_id )
        REFERENCES enderecos ( end_id );

ALTER TABLE pedidos
    ADD CONSTRAINT pedidos_produtos_fk FOREIGN KEY ( ped_pro_id )
        REFERENCES produtos ( pro_id );

ALTER TABLE pedidos
    ADD CONSTRAINT pedidos_transacoes_fk FOREIGN KEY ( ped_tra_id )
        REFERENCES transacoes ( tra_id );

ALTER TABLE pertence
    ADD CONSTRAINT pertence_categorias_produto_fk FOREIGN KEY ( per_cat_id )
        REFERENCES categorias_produto ( cat_id );

ALTER TABLE pertence
    ADD CONSTRAINT pertence_produtos_fk FOREIGN KEY ( per_pro_id )
        REFERENCES produtos ( pro_id );

ALTER TABLE produtos
    ADD CONSTRAINT produtos_grupo_precificacao_fk FOREIGN KEY ( pro_grp_id )
        REFERENCES grupo_precificacao ( grp_id );

ALTER TABLE transacoes
    ADD CONSTRAINT transacoes_clientes_fk FOREIGN KEY ( tra_cli_id )
        REFERENCES clientes ( cli_id );



-- Relatório do Resumo do Oracle SQL Developer Data Modeler: 
-- 
-- CREATE TABLE                            16
-- CREATE INDEX                             0
-- ALTER TABLE                             29
-- CREATE VIEW                              0
-- ALTER VIEW                               0
-- CREATE PACKAGE                           0
-- CREATE PACKAGE BODY                      0
-- CREATE PROCEDURE                         0
-- CREATE FUNCTION                          0
-- CREATE TRIGGER                           0
-- ALTER TRIGGER                            0
-- CREATE COLLECTION TYPE                   0
-- CREATE STRUCTURED TYPE                   0
-- CREATE STRUCTURED TYPE BODY              0
-- CREATE CLUSTER                           0
-- CREATE CONTEXT                           0
-- CREATE DATABASE                          0
-- CREATE DIMENSION                         0
-- CREATE DIRECTORY                         0
-- CREATE DISK GROUP                        0
-- CREATE ROLE                              0
-- CREATE ROLLBACK SEGMENT                  0
-- CREATE SEQUENCE                          0
-- CREATE MATERIALIZED VIEW                 0
-- CREATE MATERIALIZED VIEW LOG             0
-- CREATE SYNONYM                           0
-- CREATE TABLESPACE                        0
-- CREATE USER                              0
-- 
-- DROP TABLESPACE                          0
-- DROP DATABASE                            0
-- 
-- REDACTION POLICY                         0
-- 
-- ORDS DROP SCHEMA                         0
-- ORDS ENABLE SCHEMA                       0
-- ORDS ENABLE OBJECT                       0
-- 
-- ERRORS                                   0
-- WARNINGS                                 0
