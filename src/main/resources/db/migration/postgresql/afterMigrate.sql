-- Desativa todos os triggers, isso inclui as restrições da chave estrangeira
-- Isso permite inserir os dados sem se preocupar com a ordem das tabelas
-- https://stackoverflow.com/questions/3942258/how-do-i-temporarily-disable-triggers-in-postgresql
SET session_replication_role = 'replica';

-- Clientes
-- https://www.4devs.com.br/gerador_de_cpf
SELECT setval('clientes_cli_id_seq', 1, false);
INSERT INTO clientes (cli_nome, cli_cpf, cli_data_nascimento, cli_genero, cli_status, cli_telefone, cli_telefone_tipo, cli_email) VALUES
('Carlos Silva', '43757832060', '30-03-1986', 'MALE', 'ACTIVE', '11945653333', 'CELLPHONE', 'carlos@gmail.com'),
('Mariana Duarte', '71374904090', '15-02-1971', 'FEMALE', 'ACTIVE', '11974526335', 'TELEPHONE', 'marina.duarte@outlook.com'),
('Vanessa Von Hausten', '94551842060', '01-11-2001', 'FEMALE', 'INACTIVE', '11932301004', 'VoIP_PHONE', 'vanessa123@terra.com.br');


-- ###########################################################################################


-- CEP
-- https://www.4devs.com.br/gerador_de_cep
SELECT setval('cep_cep_id_seq', 1, false);
INSERT INTO cep(cep_cep, cep_logradouro, cep_bairro, cep_cidade, cep_estado, cep_pais) VALUES
('13424794', 'Rua das Laranjeiras', 'Vila das Rosas', 'Pindamonhangaba', 'São Paulo', 'Brasil'),
('08490690', 'Avenida Rodrigo Souza', 'Vila Madalena', 'São Paulo', 'São Paulo', 'Brasil'),
('13337550', 'Estrada dos Desalmados', 'Bela Vista', 'Ponta Grossa', 'Minas Gerais', 'Brasil'),
('11348310', 'Caminho da Luz', 'Pamonha', 'Americana', 'Paraná', 'Brasil');


-- Endereço
SELECT setval('enderecos_end_id_seq', 1, false);
INSERT INTO enderecos (end_numero, end_tipo_residencia, end_tipo_logradouro, end_frase_identificacao, end_observacao, cli_cli_id, cep_cep_id) VALUES
('123', 'HOUSE', 'STREET', 'Minha Casa', '', '1', '1'),
('234', 'OTHER', 'AVENUE', 'Trabalho', 'Horário comercial: 07:30 - 17:10', '1', '2'),
('666', 'HOUSE', 'SQUARE', 'Lar', 'Fundos', '2', '3'),
('111', 'APARTMENT', 'ALLEY', 'Reduto de paz', '', '3', '4');


-- Categoria de endereço
INSERT INTO categorias_end (cae_nome) VALUES
('RESIDENCE'),
('BILLING'),
('SHIPPING');


-- Relaciona o Endereço com sua categoria
INSERT INTO funcao (end_end_id, cae_cae_id) VALUES
('1', '1'),
('2', '2'),
('2', '3'),
('3', '1'),
('3', '2'),
('3', '3'),
('4', '1'),
('4', '2'),
('4', '3');


-- ###########################################################################################


-- Cartão de Crédito
-- https://www.invertexto.com/gerador-de-cartao-de-credito
SELECT setval('cartoes_de_credito_car_id_seq', 1, false);
INSERT INTO cartoes_de_credito (car_numero, car_bandeira, car_nome_impresso, car_codigo_seguranca, car_preferencial, cli_cli_id) VALUES
('4372412662447605', 'VISA', 'Carlos Silva', '757', (TRUE), '1'),
('5115199853098847', 'MASTERCARD', 'Carlos Silva', '543', (FALSE), '1'),
('344773538685170', 'AMERICAN_EXPRESS', 'Mariana Duarte', '7161', (TRUE), '2'),
('6011737478309686', 'DISCOVER', 'Vanessa V. Hausten', '647', (TRUE), '3');


-- ###########################################################################################


-- Cupons de Troca
SELECT setval('cupons_troca_ctr_id_seq', 1, false);
INSERT INTO cupons_troca (ctr_valor, ctr_quantidade, cli_cli_id) VALUES
('10', '1', '2'),
('30', '2', '3'),
('20', '1', '3');


-- Cupons de Promoção
SELECT setval('cupons_promocao_cpr_id_seq', 1, false);
INSERT INTO cupons_promocao (cpr_promocao_porcentagem, cli_cli_id, pro_pro_id) VALUES
('10', '2', '1'),
('50', '2', '2'),
('25', '3', '3');


-- ###########################################################################################


-- Transações
SELECT setval('transacoes_tra_id_seq', 1, false);
INSERT INTO transacoes (tra_data_compra, cli_cli_id) VALUES
('11/12/2024', '1'),
('15/12/2024', '1');


-- Pedidos que ficam dentro da transação
INSERT INTO pedidos (ped_status, ped_quantidade, tra_tra_id, pro_pro_id) VALUES
('ENTREGUE', '3', '1', '1'),
('ENTREGUE', '1', '1', '2'),
('EM_PROCESSAMENTO', '5', '2', '1');


-- ###########################################################################################


-- Produto
SELECT setval('produtos_pro_id_seq', 1, false);
INSERT INTO produtos (pro_nome_produto, pro_data_entrada, pro_quantidade_estoque, pro_quantidade_bloqueada, pro_valor_precificacao, pro_valor_custo, pro_categoria_inativacao, pro_justificativa_inativacao, grp_grp_id) VALUES
('Taco de Beisebol', '12-12-2022', '50', '0', '15', '50', 'ATIVO', '', '1'),
('Bola de Beisebol', '02-07-2020', '0', '0', '20', '120', 'FORA_DE_MERCADO', 'Sistema: Baixa venda', '1'),
('Taco de Beisebol de grafeno', '12-12-2022', '10', '0', '30', '100', 'DESATIVADO_MANUALMENTE', 'O produto possui sanções estrangeiras', '1'),
('Bola de Beisebol de poliamida', '12-12-2022', '7', '0', '30', '50', 'ATIVO', '', '3'),
('Rede de vôlei', '12-12-2022', '20', '0', '20', '50', 'ATIVO', '', '2'),
('Taco de golfe', '12-12-2022', '2', '0', '50', '50', 'ATIVO', '', '4');


-- Grupo de precificação (Margem de lucro em porcentagem)
SELECT setval('grupo_precificacao_grp_id_seq', 1, false);
INSERT INTO grupo_precificacao (grp_valor_precificacao, grp_nome) VALUES
('10', 'STANDARD'),
('20', 'PLUS'),
('30', 'DELUXE'),
('50', 'PREMIUM');


-- Categorias do produto
SELECT setval('categorias_produto_cat_id_seq', 1, false);
INSERT INTO categorias_produto (cat_nome) VALUES
('Vôlei'),
('Beisebol'),
('Golfe'),
('Bola'),
('Taco');


-- Relaciona os produtos com suas categorias
INSERT INTO pertence (pro_pro_id, cat_cat_id) VALUES
('1', '2'),
('1', '5'),
('2', '2'),
('2', '4'),
('3', '2'),
('3', '5'),
('4', '2'),
('4', '4'),
('5', '1'),
('6', '3'),
('6', '5');


-- ###########################################################################################


-- Inicia a sequência do log em 1
SELECT setval('logs_log_id_seq', 1, false);

-- Administrador
SELECT setval('administrador_adm_id_seq', 1, false);
INSERT INTO administrador (adm_nome, adm_email) VALUES
('Fábio', 'fabio@esportiva.com.br'),
('Lucas', 'lucas@esportiva.com.br');

-- Ativa as restrições da chave estrangeira novamente
SET session_replication_role = DEFAULT;
