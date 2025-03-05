INSERT INTO clientes (cli_nome, cli_cpf, cli_data_nascimento, cli_genero, cli_status, cli_telefone, cli_telefone_tipo, cli_email) VALUES
('Carlos Silva', '43757832060', '1986-03-30', 'MASCULINO', 'ATIVO', '11945653333', 'TELEFONE', 'carlos@gmail.com'),
('Mariana Duarte', '71374904090', '1971-02-15', 'FEMININO', 'ATIVO', '11974526335', 'TELEFONE', 'marina.duarte@outlook.com'),
('Vanessa Von Hausten', '94551842060', '2001-11-01', 'FEMININO', 'INATIVO', '11932301004', 'TELEFONE_VoIP', 'vanessa123@terra.com.br');

INSERT INTO cep(cep_cep, cep_logradouro, cep_bairro, cep_cidade, cep_estado, cep_pais) VALUES
('13424794', 'Rua das Laranjeiras', 'Vila das Rosas', 'Pindamonhangaba', 'São Paulo', 'Brasil'),
('08490690', 'Avenida Rodrigo Souza', 'Vila Madalena', 'São Paulo', 'São Paulo', 'Brasil'),
('13337550', 'Estrada dos Desalmados', 'Bela Vista', 'Ponta Grossa', 'Minas Gerais', 'Brasil'),
('11348310', 'Caminho da Luz', 'Pamonha', 'Americana', 'Paraná', 'Brasil');

INSERT INTO enderecos (end_numero, end_tipo_residencia, end_tipo_logradouro, end_frase_identificacao, end_observacao, end_cli_id, end_cep_id) VALUES
('123', 'CASA', 'RUA', 'Minha Casa', '', '1', '1'),
('234', 'OUTROS', 'AVENIDA', 'Trabalho', 'Horário comercial: 07:30 - 17:10', '1', '2'),
('666', 'CASA', 'VIELA', 'Lar', 'Fundos', '2', '3'),
('111', 'APARTAMENTO', 'RODOVIA', 'Reduto de paz', '', '3', '4');

INSERT INTO categorias_end (cae_nome) VALUES
('RESIDENCIA'),
('COBRANCA'),
('ENTREGA');

INSERT INTO funcao (fun_end_id, fun_cae_id) VALUES
('1', '1'),
('2', '1'),
('2', '3'),
('3', '1'),
('3', '2'),
('3', '3'),
('4', '1'),
('4', '2'),
('4', '3');


INSERT INTO administrador (adm_nome, adm_email) VALUES
('Fábio', 'fabio@email.com'),
('Lucas', 'lucas@email.com');
