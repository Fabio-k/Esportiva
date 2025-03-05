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
    car_codigo_seguranca  INTEGER NOT NULL,
    car_preferencial      BOOLEAN NOT NULL,
    cli_cli_id            INTEGER NOT NULL
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
    cli_id               SERIAL PRIMARY KEY,
    cli_nome             VARCHAR(50) NOT NULL,
    cli_cpf              VARCHAR(11) NOT NULL,
    cli_data_nascimento  DATE NOT NULL,
    cli_genero           VARCHAR(10) NOT NULL,
    cli_status           VARCHAR(10) NOT NULL,
    cli_telefone         VARCHAR(13) NOT NULL,
    cli_telefone_tipo    VARCHAR(20) NOT NULL,
    cli_email            VARCHAR(50) NOT NULL
);


CREATE TABLE cupons_promocao (
    cpr_id                SERIAL PRIMARY KEY,
    cpr_promocao_porcentagem  FLOAT NOT NULL,
    cli_cli_id            INTEGER NOT NULL,
    pro_pro_id            INTEGER NOT NULL
);


CREATE TABLE cupons_troca (
    ctr_id           SERIAL PRIMARY KEY,
    ctr_valor        FLOAT NOT NULL,
    ctr_quantidade   INTEGER NOT NULL,
    cli_cli_id       INTEGER NOT NULL
);


CREATE TABLE enderecos (
    end_id                   SERIAL PRIMARY KEY,
    end_numero               INTEGER NOT NULL,
    end_tipo_residencia      VARCHAR(20) NOT NULL,
    end_tipo_logradouro      VARCHAR(20) NOT NULL,
    end_frase_identificacao  VARCHAR(40) NOT NULL,
    end_observacao           VARCHAR(50),
    cli_cli_id               INTEGER NOT NULL,
    cep_cep_id               INTEGER NOT NULL
);


CREATE TABLE funcao (
    end_end_id  INTEGER NOT NULL,
    cae_cae_id  INTEGER NOT NULL
);

ALTER TABLE funcao ADD CONSTRAINT funcao_pk PRIMARY KEY ( end_end_id,
                                                          cae_cae_id );

CREATE TABLE grupo_precificacao (
    grp_id                  SERIAL PRIMARY KEY,
    grp_valor_precificacao  FLOAT NOT NULL,
    grp_nome                VARCHAR(20) NOT NULL
);

CREATE TABLE logs (
    log_id                  SERIAL PRIMARY KEY,
    log_usuario             VARCHAR(10) NOT NULL,
    log_data_hora           TIMESTAMP NOT NULL,
    log_operacao            VARCHAR(10) NOT NULL,
    log_conteudo_alteracao  JSON NOT NULL
);

CREATE TABLE pedidos (
    ped_status         VARCHAR(20) NOT NULL,
    ped_quantidade     INTEGER NOT NULL,
    tra_tra_id         INTEGER NOT NULL,
    pro_pro_id         INTEGER
);

CREATE TABLE pertence (
    pro_pro_id  INTEGER NOT NULL,
    cat_cat_id  INTEGER NOT NULL
);

ALTER TABLE pertence ADD CONSTRAINT pertence_pk PRIMARY KEY ( pro_pro_id,
                                                              cat_cat_id );

CREATE TABLE produtos (
    pro_id                        SERIAL PRIMARY KEY,
    pro_nome_produto              VARCHAR(30) NOT NULL,
    pro_data_entrada              DATE NOT NULL,
    pro_quantidade_estoque        INTEGER NOT NULL,
    pro_quantidade_bloqueada      INTEGER NOT NULL,
    pro_valor_precificacao        FLOAT NOT NULL,
    pro_valor_custo               FLOAT NOT NULL,
    pro_categoria_inativacao      VARCHAR(23) NOT NULL,
    pro_justificativa_inativacao  VARCHAR(50) NOT NULL,
    grp_grp_id                    INTEGER
);


CREATE TABLE transacoes (
    tra_id           SERIAL PRIMARY KEY,
    tra_data_compra  DATE NOT NULL,
    cli_cli_id       INTEGER NOT NULL
);


ALTER TABLE cartoes_de_credito
    ADD CONSTRAINT cartoes_de_credito_clientes_fk FOREIGN KEY ( cli_cli_id )
        REFERENCES clientes ( cli_id );

ALTER TABLE cupons_promocao
    ADD CONSTRAINT cupons_promocao_clientes_fk FOREIGN KEY ( cli_cli_id )
        REFERENCES clientes ( cli_id );

ALTER TABLE cupons_promocao
    ADD CONSTRAINT cupons_promocao_produtos_fk FOREIGN KEY ( pro_pro_id )
        REFERENCES produtos ( pro_id );

ALTER TABLE cupons_troca
    ADD CONSTRAINT cupons_troca_clientes_fk FOREIGN KEY ( cli_cli_id )
        REFERENCES clientes ( cli_id );

ALTER TABLE enderecos
    ADD CONSTRAINT enderecos_cep_fk FOREIGN KEY ( cep_cep_id )
        REFERENCES cep ( cep_id );

ALTER TABLE enderecos
    ADD CONSTRAINT enderecos_clientes_fk FOREIGN KEY ( cli_cli_id )
        REFERENCES clientes ( cli_id );

ALTER TABLE funcao
    ADD CONSTRAINT funcao_categorias_end_fk FOREIGN KEY ( cae_cae_id )
        REFERENCES categorias_end ( cae_id );

ALTER TABLE funcao
    ADD CONSTRAINT funcao_enderecos_fk FOREIGN KEY ( end_end_id )
        REFERENCES enderecos ( end_id );

ALTER TABLE pedidos
    ADD CONSTRAINT pedidos_produtos_fk FOREIGN KEY ( pro_pro_id )
        REFERENCES produtos ( pro_id );

ALTER TABLE pedidos
    ADD CONSTRAINT pedidos_transacoes_fk FOREIGN KEY ( tra_tra_id )
        REFERENCES transacoes ( tra_id );

ALTER TABLE pertence
    ADD CONSTRAINT pertence_categorias_produto_fk FOREIGN KEY ( cat_cat_id )
        REFERENCES categorias_produto ( cat_id );

ALTER TABLE pertence
    ADD CONSTRAINT pertence_produtos_fk FOREIGN KEY ( pro_pro_id )
        REFERENCES produtos ( pro_id );

ALTER TABLE produtos
    ADD CONSTRAINT produtos_grupo_precificacao_fk FOREIGN KEY ( grp_grp_id )
        REFERENCES grupo_precificacao ( grp_id );

ALTER TABLE transacoes
    ADD CONSTRAINT transacoes_clientes_fk FOREIGN KEY ( cli_cli_id )
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
