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
    pro_quantidade_bloqueada, pro_margem_lucro, pro_valor_custo,
    pro_categoria_inativacao, pro_justificativa_inativacao, pro_grp_id, pro_descricao, pro_imagem
) VALUES
('Bola de Vôlei Mikasa 350VW', '2024-03-01', 25, 0, 25.0, 540.90, 'ATIVO', 'N/A', 2, 'Perfeita para treinos e competições, a Mikasa 350VW é uma bola oficial de vôlei com tecnologia avançada para melhor aderência e controle. Seu design aerodinâmico e material de alta qualidade garantem jogadas precisas e maior durabilidade. Ideal para atletas e entusiastas do esporte.', '/images/products/mikasa.png'),
('Tênis Adidas CourtJam', '2024-02-15', 6, 0, 30.0, 250.00, 'ATIVO', 'N/A', 3, 'O Adidas CourtJam combina conforto e desempenho para quem pratica tênis. Com amortecimento responsivo e solado aderente, oferece estabilidade e suporte para movimentos rápidos na quadra. Seu design moderno e respirável proporciona um ajuste confortável e duradouro.', '/images/products/tenis.png'),
('Camisa Nike Dry Fit', '2025-03-05', 15, 0, 20.0, 80.00, 'ATIVO', 'N/A', 2, 'Desenvolvida para atletas que buscam conforto e desempenho, a camisa Nike Dry Fit é feita com tecido respirável e tecnologia de absorção de suor, mantendo o corpo seco e confortável durante os treinos e competições. Leve, flexível e estilosa, é ideal para qualquer atividade esportiva.', '/images/products/camiseta.png'),
('Caneleira Puma Pro', '2025-02-20', 20, 0, 15.0, 45.00, 'ATIVO', 'N/A', 1, 'Oferecendo proteção e conforto, a caneleira Puma Pro é essencial para a segurança dos jogadores. Feita com material resistente e design anatômico, proporciona excelente absorção de impacto sem comprometer a mobilidade. Ideal para treinos e jogos intensos.', '/images/products/caneleta.png'),
('Rede de Volei de Praia BV100 Copaya', '2025-03-05', 8, 0, 0.0, 229.99, 'ATIVO', 'N/A', 2, 'deal para partidas de vôlei de praia, essa rede BV100 da Copaya oferece resistência e durabilidade. Feita com materiais de alta qualidade, garante uma experiência de jogo segura e profissional.', '/images/products/rede.png'),
('Kit de Marcação Vôlei de Quadra BV900 Copaya', '2025-02-20', 10, 0, 0.0, 399.99, 'ATIVO', 'N/A', 3, 'Defina os limites da quadra com precisão! O kit de marcação BV900 da Copaya é perfeito para jogadores e treinadores que buscam organizar jogos e treinos de forma prática e eficiente.', '/images/products/marcacao_volei.png'),
('Bola de Futebol de Campo Topper Slick - Branca e Azul', '2024-02-15', 10, 0, 0.0, 79.80, 'INATIVO', 'N/A', 1,  'Com um design moderno e aerodinâmico, a bola Topper Slick é ideal para partidas de futebol de campo. Fabricada com material resistente, proporciona excelente controle e durabilidade.', '/images/products/topper.png'),
('Manguitos de Vôlei V100 VAP', '2025-01-24', 8, 0, 0.0, 99.99, 'ATIVO' , 'N/A', 3 , 'Proteja seus braços e melhore seu desempenho com os manguitos V100 da VAP. Perfeitos para absorver impacto e suor, eles oferecem conforto e segurança durante os treinos e competições.', '/images/products/manguito.png'),
('Joelheira Vôlei Kipsta VKP100', '2025-01-24', 11, 0, 0.0, 99.99, 'INATIVO', 'N/A', 1, 'Desenvolvida para garantir proteção e conforto, a joelheira VKP100 da Kipsta absorve impactos e reduz riscos de lesões. Essencial para jogadores que buscam segurança nas partidas.', '/images/products/joelheira.png'),
('Bola Vôlei Penalty VP 5000 X', '2025-01-16', 13, 0, 0.0, 154.90, 'ATIVO', 'N/A', 2, 'A Penalty VP 5000 X é referência em qualidade e performance no vôlei. Com excelente grip e estrutura resistente, é ideal para treinos e competições de alto nível.', '/images/products/penaltyvp5000.png');



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
