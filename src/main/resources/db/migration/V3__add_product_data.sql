INSERT INTO categorias_produto (cat_nome) VALUES
('Vôlei'),
('Tênis'),
('Roupas'),
('Acessórios'),
('Futebol');

INSERT INTO grupo_precificacao (grp_nome, grp_margem_lucro) VALUES
('Baixa', 0.1),
('Média', 0.2),
('Alta',  0.3);

INSERT INTO produtos (
    pro_nome_produto, pro_data_entrada, pro_quantidade_estoque,
    pro_quantidade_bloqueada, pro_valor_custo,
    pro_categoria_inativacao, pro_justificativa_inativacao, pro_grp_id, pro_descricao, pro_imagem
) VALUES
('Bola de Vôlei Mikasa 350VW', '2024-03-01', 25, 7, 540.90, 'ATIVO', 'N/A', 2, 'Perfeita para treinos e competições. Ideal para atletas e entusiastas do esporte.', '/images/products/mikasa.png'),
('Tênis Adidas CourtJam', '2024-02-15', 6, 1, 250.00, 'ATIVO', 'N/A', 3, 'O Adidas CourtJam combina conforto e desempenho para quem pratica tênis. Seu design moderno e respirável proporciona um ajuste confortável e duradouro.', '/images/products/tenis.png'),
('Camisa Nike Dry Fit', '2025-03-05', 15, 7, 80.00, 'ATIVO', 'N/A', 2, 'Desenvolvida para atletas que buscam conforto e desempenho. Leve, flexível e estilosa, é ideal para qualquer atividade esportiva.', '/images/products/camiseta.png'),
('Caneleira Puma Pro', '2025-02-20', 20, 0, 45.00, 'ATIVO', 'N/A', 1, 'Oferecendo proteção e conforto, a caneleira Puma Pro é essencial para a segurança dos jogadores. Ideal para treinos e jogos intensos.', '/images/products/caneleta.png'),
('Rede de Volei de Praia BV100 Copaya', '2025-03-05', 10, 10, 229.99, 'ATIVO', 'N/A', 2, 'Ideal para partidas de vôlei de praia, essa rede BV100 da Copaya oferece resistência e durabilidade.', '/images/products/rede.png'),
('Kit de Marcação Vôlei de Quadra BV900 Copaya', '2025-02-20', 10, 0, 399.99, 'ATIVO', 'N/A', 3, 'Perfeito para jogadores e treinadores que buscam organizar jogos e treinos de forma prática e eficiente.', '/images/products/marcacao_volei.png'),
('Bola de Futebol de Campo Topper Slick - Branca e Azul', '2024-02-15', 10, 0, 79.80, 'INATIVO', 'N/A', 1,  'Ideal para partidas de futebol de campo. Fabricada com material resistente, proporciona excelente controle e durabilidade.', '/images/products/topper.png'),
('Manguitos de Vôlei V100 VAP', '2025-01-24', 8, 0, 99.99, 'ATIVO' , 'N/A', 3 , 'Perfeitos para absorver impacto e suor, eles oferecem conforto e segurança durante os treinos e competições.', '/images/products/manguito.png'),
('Joelheira Vôlei Kipsta VKP100', '2025-01-24', 11, 5, 99.99, 'INATIVO', 'N/A', 1, 'Desenvolvida para garantir proteção e conforto. Essencial para jogadores que buscam segurança nas partidas.', '/images/products/joelheira.png'),
('Bola Vôlei Penalty VP 5000 X', '2025-01-16', 13, 0, 154.90, 'ATIVO', 'N/A', 2, 'Com excelente grip e estrutura resistente, é ideal para treinos e competições de alto nível.', '/images/products/penaltyvp5000.png');



INSERT INTO pertence (per_pro_id, per_cat_id) VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 1),
(6, 1),
(7, 5),
(8, 1),
(9, 1),
(10,1);
