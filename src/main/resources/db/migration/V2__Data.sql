INSERT INTO clientes (cli_nome, cli_cpf, cli_data_nascimento, cli_genero, cli_status, cli_telefone, cli_telefone_tipo, cli_email) VALUES
('Carlos Silva', '43757832060', '1986-03-30', 'MASCULINO', 'ATIVO', '11945653333', 'TELEFONE', 'carlos@gmail.com'),
('Mariana Duarte', '71374904090', '1971-02-15', 'FEMININO', 'ATIVO', '11974526335', 'TELEFONE', 'marina.duarte@outlook.com'),
('Vanessa Von Hausten', '94551842060', '2001-11-01', 'FEMININO', 'INATIVO', '11932301004', 'TELEFONE_VoIP', 'vanessa123@terra.com.br');

INSERT INTO cartoes_de_credito (car_numero, car_bandeira, car_nome_impresso, car_codigo_seguranca, car_cli_id) VALUES
('4372412662447605', 'VISA', 'Carlos Silva', '757', '1'),
('5115199853098847', 'MASTERCARD', 'Carlos Silva', '543', '1'),
('344773538685170', 'AMERICAN_EXPRESS', 'Mariana Duarte', '7161', '2'),
('450063570000745', 'DISCOVER', 'Mariana D.', '349', '2'),
('6011737478309686', 'DISCOVER', 'Vanessa V. Hausten', '647', '3');

INSERT INTO cep(cep_cep, cep_logradouro, cep_bairro, cep_cidade, cep_estado, cep_pais) VALUES
('08343000', 'Travessa Somos Todos Iguais', 'Jardim da Conquista', 'São Paulo', 'São Paulo', 'Brasil'),
('60332070', 'Rua Grito de Alerta', 'Barra do Ceará', 'Fortaleza', 'Ceará', 'Brasil'),
('35407971', 'Rua Rodrigo Silva', 'Vila Aparecida', 'Ouro Preto', 'Minas Gerais', 'Brasil'),
('52191440', 'Rua Só Nós Dois', 'Brejo de Beberibe', 'Recife', 'Pernambuco',  'Brasil');

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


INSERT INTO cupons_troca(ctr_valor, ctr_cli_id) VALUES
('50', '1'),
('20', '1'),
('60', '2'),
('65', '3'),
('20', '3');


INSERT INTO administrador (adm_nome, adm_email) VALUES
('Fábio', 'fabio@esportiva.com'),
('Lucas', 'lucas@esportiva.com');

