-- Gerado por Oracle SQL Developer Data Modeler 20.2.0.167.1538
--   em:        2025-03-01 13:42:47 BRT
--   site:      Oracle Database 11g
--   tipo:      Oracle Database 11g



-- predefined type, no DDL - MDSYS.SDO_GEOMETRY

-- predefined type, no DDL - XMLTYPE

CREATE TABLE cartoes_de_credito (
    car_numero            VARCHAR2(19 CHAR) NOT NULL,
    car_bandeira          VARCHAR2(20 CHAR) NOT NULL,
    car_nome_impresso     VARCHAR2(30 CHAR) NOT NULL,
    car_codigo_seguranca  INTEGER NOT NULL,
    clientes_cli_id       INTEGER NOT NULL
);

ALTER TABLE cartoes_de_credito ADD CONSTRAINT cartoes_de_credito_pk PRIMARY KEY ( car_numero,
                                                                                  car_bandeira );

CREATE TABLE categorias_produto (
    cat_id    INTEGER NOT NULL,
    cat_nome  VARCHAR2(20 CHAR) NOT NULL
);

ALTER TABLE categorias_produto ADD CONSTRAINT categorias_produto_pk PRIMARY KEY ( cat_id );

CREATE TABLE cep (
    cep_cep         VARCHAR2(8 CHAR) NOT NULL,
    cep_logradouro  VARCHAR2(50 CHAR) NOT NULL,
    cep_bairro      VARCHAR2(50 CHAR) NOT NULL,
    cep_cidade      VARCHAR2(50 CHAR) NOT NULL,
    cep_estado      VARCHAR2(50 CHAR) NOT NULL,
    cep_pais        VARCHAR2(50 CHAR) NOT NULL,
    cep_id          INTEGER NOT NULL
);

ALTER TABLE cep ADD CONSTRAINT cep_pk PRIMARY KEY ( cep_id );

CREATE TABLE clientes (
    cli_id               INTEGER NOT NULL,
    cli_data_nascimento  DATE NOT NULL,
    cli_cpf              VARCHAR2(11 CHAR) NOT NULL,
    cli_status           VARCHAR2(10 CHAR) NOT NULL,
    cli_nome             VARCHAR2(50 CHAR) NOT NULL,
    cli_genero           VARCHAR2(10 CHAR) NOT NULL
);

ALTER TABLE clientes ADD CONSTRAINT clientes_pk PRIMARY KEY ( cli_id );

CREATE TABLE cupons_promocao (
    cpr_id                INTEGER NOT NULL,
    promocao_porcentagem  FLOAT NOT NULL,
    clientes_cli_id       INTEGER NOT NULL,
    produtos_pro_id       INTEGER NOT NULL
);

ALTER TABLE cupons_promocao ADD CONSTRAINT cupons_promocao_pk PRIMARY KEY ( cpr_id );

CREATE TABLE cupons_troca (
    ctr_id           INTEGER NOT NULL,
    ctr_valor        FLOAT NOT NULL,
    ctr_quantidade   INTEGER NOT NULL,
    clientes_cli_id  INTEGER NOT NULL
);

ALTER TABLE cupons_troca ADD CONSTRAINT cupons_troca_pk PRIMARY KEY ( ctr_id );

CREATE TABLE emails (
    ema_email        VARCHAR2(50 CHAR) NOT NULL,
    clientes_cli_id  INTEGER NOT NULL
);

ALTER TABLE emails ADD CONSTRAINT emails_pk PRIMARY KEY ( ema_email );

CREATE TABLE enderecos (
    end_id                  INTEGER NOT NULL,
    end_numero              INTEGER NOT NULL,
    end_tipo_residencia     VARCHAR2(20 CHAR) NOT NULL,
    end_observacao          VARCHAR2(50 CHAR),
    end_tipo_logradouro     VARCHAR2(20 CHAR) NOT NULL,
    end_categoria_endereco  VARCHAR2(20 CHAR) NOT NULL,
    clientes_cli_id         INTEGER NOT NULL,
    cep_cep_id              INTEGER NOT NULL
);

ALTER TABLE enderecos ADD CONSTRAINT enderecos_pk PRIMARY KEY ( end_id );

CREATE TABLE grupo_precificacao (
    grp_id                  INTEGER NOT NULL,
    grp_valor_precificacao  FLOAT NOT NULL,
    grp_nome                VARCHAR2(20 CHAR) NOT NULL
);

ALTER TABLE grupo_precificacao ADD CONSTRAINT grupo_precificacao_pk PRIMARY KEY ( grp_id );

CREATE TABLE logs (
    log_id                  INTEGER NOT NULL,
    log_operacao            VARCHAR2(10 CHAR) NOT NULL,
    log_usuario             VARCHAR2(10 CHAR) NOT NULL,
    log_data_hora           DATE NOT NULL,
    log_conteudo_alteracao  VARCHAR2(50 CHAR) NOT NULL
);

ALTER TABLE logs ADD CONSTRAINT logs_pk PRIMARY KEY ( log_id );

CREATE TABLE pedidos (
    ped_status         VARCHAR2(20 CHAR) NOT NULL,
    ped_quantidade     INTEGER NOT NULL,
    transacoes_tra_id  INTEGER NOT NULL,
    produtos_pro_id    INTEGER
);

CREATE TABLE pertence (
    produtos_pro_id            INTEGER NOT NULL,
    categorias_produto_cat_id  INTEGER NOT NULL
);

ALTER TABLE pertence ADD CONSTRAINT pertence_pk PRIMARY KEY ( produtos_pro_id,
                                                              categorias_produto_cat_id );

CREATE TABLE produtos (
    pro_id                        INTEGER NOT NULL,
    pro_quantidade_estoque        INTEGER NOT NULL,
    pro_quantidade_bloqueada      INTEGER NOT NULL,
    pro_valor_precificacao        FLOAT NOT NULL,
    pro_justificativa_inativacao  VARCHAR2(50 CHAR) NOT NULL,
    pro_categoria_inativacao      VARCHAR2(20 CHAR) NOT NULL,
    pro_valor_custo               FLOAT NOT NULL,
    pro_data_entrada              DATE NOT NULL,
    pro_nome_produto              VARCHAR2(30 CHAR) NOT NULL,
    grupo_precificacao_grp_id     INTEGER
);

ALTER TABLE produtos ADD CONSTRAINT produtos_pk PRIMARY KEY ( pro_id );

CREATE TABLE telefones (
    tel_telefone     VARCHAR2(13 CHAR) NOT NULL,
    clientes_cli_id  INTEGER NOT NULL
);

ALTER TABLE telefones ADD CONSTRAINT telefones_pk PRIMARY KEY ( tel_telefone );

CREATE TABLE transacoes (
    tra_id           INTEGER NOT NULL,
    tra_data_compra  DATE NOT NULL,
    clientes_cli_id  INTEGER NOT NULL
);

ALTER TABLE transacoes ADD CONSTRAINT transacoes_pk PRIMARY KEY ( tra_id );

ALTER TABLE cartoes_de_credito
    ADD CONSTRAINT cartoes_de_credito_clientes_fk FOREIGN KEY ( clientes_cli_id )
        REFERENCES clientes ( cli_id );

ALTER TABLE cupons_promocao
    ADD CONSTRAINT cupons_promocao_clientes_fk FOREIGN KEY ( clientes_cli_id )
        REFERENCES clientes ( cli_id );

ALTER TABLE cupons_promocao
    ADD CONSTRAINT cupons_promocao_produtos_fk FOREIGN KEY ( produtos_pro_id )
        REFERENCES produtos ( pro_id );

ALTER TABLE cupons_troca
    ADD CONSTRAINT cupons_troca_clientes_fk FOREIGN KEY ( clientes_cli_id )
        REFERENCES clientes ( cli_id );

ALTER TABLE emails
    ADD CONSTRAINT emails_clientes_fk FOREIGN KEY ( clientes_cli_id )
        REFERENCES clientes ( cli_id );

ALTER TABLE enderecos
    ADD CONSTRAINT enderecos_cep_fk FOREIGN KEY ( cep_cep_id )
        REFERENCES cep ( cep_id );

ALTER TABLE enderecos
    ADD CONSTRAINT enderecos_clientes_fk FOREIGN KEY ( clientes_cli_id )
        REFERENCES clientes ( cli_id );

ALTER TABLE pedidos
    ADD CONSTRAINT pedidos_produtos_fk FOREIGN KEY ( produtos_pro_id )
        REFERENCES produtos ( pro_id );

ALTER TABLE pedidos
    ADD CONSTRAINT pedidos_transacoes_fk FOREIGN KEY ( transacoes_tra_id )
        REFERENCES transacoes ( tra_id );

ALTER TABLE pertence
    ADD CONSTRAINT pertence_categorias_produto_fk FOREIGN KEY ( categorias_produto_cat_id )
        REFERENCES categorias_produto ( cat_id );

ALTER TABLE pertence
    ADD CONSTRAINT pertence_produtos_fk FOREIGN KEY ( produtos_pro_id )
        REFERENCES produtos ( pro_id );

ALTER TABLE produtos
    ADD CONSTRAINT produtos_grupo_precificacao_fk FOREIGN KEY ( grupo_precificacao_grp_id )
        REFERENCES grupo_precificacao ( grp_id );

ALTER TABLE telefones
    ADD CONSTRAINT telefones_clientes_fk FOREIGN KEY ( clientes_cli_id )
        REFERENCES clientes ( cli_id );

ALTER TABLE transacoes
    ADD CONSTRAINT transacoes_clientes_fk FOREIGN KEY ( clientes_cli_id )
        REFERENCES clientes ( cli_id );



-- Relatório do Resumo do Oracle SQL Developer Data Modeler: 
-- 
-- CREATE TABLE                            15
-- CREATE INDEX                             0
-- ALTER TABLE                             28
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
