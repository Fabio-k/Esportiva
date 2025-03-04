CREATE TABLE administrador (
    adm_id    SERIAL PRIMARY KEY,
    adm_nome  VARCHAR(30) NOT NULL,
    adm_email VARCHAR(30) NOT NULL
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

CREATE TABLE enderecos (
    end_id                   SERIAL PRIMARY KEY,
    end_numero               INTEGER NOT NULL,
    end_tipo_residencia      VARCHAR(20) NOT NULL,
    end_tipo_logradouro      VARCHAR(20) NOT NULL,
    end_frase_identificacao  VARCHAR(40) NOT NULL,
    end_observacao           VARCHAR(50),
    end_cep_id               INTEGER NOT NULL,
    end_cli_id BIGINT NOT NULL,
    CONSTRAINT fk_end_cli_id FOREIGN KEY (end_cli_id) REFERENCES clientes(cli_id),
    CONSTRAINT fk_end_cep_id FOREIGN KEY (end_cep_id) REFERENCES cep(cep_id)
);

CREATE TABLE categorias_end (
    cae_cat_id SERIAL PRIMARY KEY,
    cae_cat_end VARCHAR(20) NOT NULL
);

CREATE TABLE funcao (
    fun_end_id            INTEGER NOT NULL,
    fun_cae_cat_id  INTEGER NOT NULL,
    CONSTRAINT fk_fun_end FOREIGN KEY (fun_end_id) REFERENCES enderecos(end_id),
    CONSTRAINT fk_fun_cae_cat_id FOREIGN KEY (fun_cae_cat_id) REFERENCES categorias_end(cae_cat_id)
);