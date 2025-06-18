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
('Bola Vôlei Penalty VP 5000 X', '2025-01-16', 13, 0, 154.90, 'ATIVO', 'N/A', 2, 'Com excelente grip e estrutura resistente, é ideal para treinos e competições de alto nível.', '/images/products/penaltyvp5000.png'),

('Vara de pescar', '2023-02-28', 9, 0, 75.00, 'ATIVO', 'N/A', 1, 'Item essencial para qualquer pescaria, sendo um produto leve para carregar e ideal para peixes', '/images/products/Vara de pescar.webp'),
('Taco de golfe', '2023-02-28', 9, 0, 100.00, 'ATIVO', 'N/A', 3, 'Taco resistente e versátil para jogar em campos de grama e terrenos arenosos', '/images/products/Taco de golfe.jpg'),
('Taco de beisebol', '2023-02-28', 9, 0, 38.00, 'ATIVO', 'N/A', 1, 'Taco de madeira legalizada e tratada, possui alta durabilidade e resistentes, ideal para partidas mesmo em climas chuvosos', '/images/products/Taco de beisebol.webp'),
('Squeeze', '2023-02-28', 9, 0, 3.00, 'ATIVO', 'N/A', 1, 'Garrafa livre de plástico ABS, ideal para levar água e sucos, sem fixar o gosto no plástico', '/images/products/Squeeze.jpg'),
('Saco de dormir', '2023-02-28', 9, 0, 21.00, 'ATIVO', 'N/A', 2, 'Saco de alta proteção térmica, ideal para climas extremos, como zonas temperadas', '/images/products/Saco de dormir.webp'),

('Raquete de tênis', '2023-02-28', 9, 0, 50.00, 'ATIVO', 'N/A', 1, 'Raquete de alta durabilidade, com fibras de nylon, ideal para partidas intensas e uso profissional', '/images/products/Raquete de tênis.webp'),
('Raquete de tênis de mesa', '2023-02-28', 9, 0, 8.00, 'ATIVO', 'N/A', 1, 'Raquete revista com borracha, ideal para amortecer a bola e permitir fintas melhores', '/images/products/Raquete de tênis de mesa.jpg'),
('Pino de golfe', '2023-02-28', 9, 0, 1.00, 'ATIVO', 'N/A', 3, 'Pino de golfe com plástico expandido, extremamente leve e resistente para as partidas de golfe', '/images/products/Pino de golfe.jpg'),
('Picareta de alpinismo', '2023-02-28', 9, 0, 130.00, 'ATIVO', 'N/A', 2, 'Picareta para escaladas em montanhas rochosas, consegue com pouca força penetrar em arenito e granito', '/images/products/Picareta de alpinismo.webp'),
('Óculos de natação', '2023-02-28', 9, 0, 36.00, 'ATIVO', 'N/A', 1, 'Óculos para águas salinas ou cloradas, protegendo os olhos da irritação comuns em esportes aquáticos', '/images/products/Óculos de natação.webp'),

('Mesa de tênis de mesa', '2023-02-28', 9, 0, 180.00, 'ATIVO', 'N/A', 1, 'Mesa de madeira compensada, com marcações bem definidas, ideal para esportistas amadores', '/images/products/Mesa de tênis de mesa.webp'),
('Luva de goleiro', '2023-02-28', 9, 0, 80.00, 'ATIVO', 'N/A', 1, 'Luva para futebol de campo ou de salão, resistente a impacto', '/images/products/Luva de goleiro.webp'),
('Luva de boxe', '2023-02-28', 9, 0, 110.00, 'ATIVO', 'N/A', 1, 'Luva para boxe, kickboxe e MMA. Possui alta durabilidade e amortece os socos, evitando lesões', '/images/products/Luva de boxe.webp'),
('Luva de beisebol', '2023-02-28', 9, 0, 45.00, 'ATIVO', 'N/A', 2, 'Luva para partidas amadoras de beisebol', '/images/products/Luva de beisebol.webp'),
('Lanterna de alpinismo', '2023-02-28', 9, 0, 50.00, 'ATIVO', 'N/A', 2, 'Lanterna com altos lúmens, ideal para explorar cavernas e ravinas', '/images/products/Lanterna de alpinismo.png'),

('Lanterna de acampamento', '2023-02-28', 9, 0, 22.00, 'ATIVO', 'N/A', 2, 'Lanterna de querosene para iluminar o acampamento, com foco em locais abertos como floresta', '/images/products/Lanterna de acampamento.jpg'),
('Isca de pesca', '2023-02-28', 9, 0, 12.00, 'ATIVO', 'N/A', 1, 'Isca que simula o movimento errante de uma sardinha, ideal para pescar peixes grandes como salmão', '/images/products/Isca de pesca.webp'),
('Fogão de acampamento', '2023-02-28', 9, 0, 300.00, 'ATIVO', 'N/A', 3, 'Fogão a gás ideal para acampamento ao ar livre, possui um bocal para panelas pequenas', '/images/products/Fogão de acampamento.webp'),
('Disco de frisbee', '2023-02-28', 9, 0, 14.00, 'ATIVO', 'N/A', 1, 'Disco de plástico, para brincar com humanos ou animais de estimação', '/images/products/Disco de frisbee.jpg'),
('Corda de alpinismo', '2023-02-28', 9, 0, 25.00, 'ATIVO', 'N/A', 2, 'Corda de fibras polimérica, possui alta leveza, resistência a água e a abrasão', '/images/products/Corda de alpinismo.jpg'),

('Contador de passos', '2023-02-28', 9, 0, 70.00, 'ATIVO', 'N/A', 1, 'Aparelho que permite contar passos em uma corrida com um acelerômetro, ideal para acompanhamento médico ou esportistas de alto desempenho', '/images/products/Contador de passos.jpg'),
('Chuteira', '2023-02-28', 9, 0, 77.00, 'ATIVO', 'N/A', 1, 'Chuteira com travas, desenvolvida para futebol em campo de grama, terra ou areia', '/images/products/Chuteira.png'),
('Capacete de futebol americano', '2023-02-28', 9, 0, 90.00, 'ATIVO', 'N/A', 1, 'Capacete com proteção contra impactos, ideal para esportes brutos', '/images/products/Capacete de futebol americano.jpg'),
('Bola de tênis de mesa', '2023-02-28', 9, 0, 2.00, 'ATIVO', 'N/A', 1, 'Bola de tênis leve e com cor laranja aeronáutico para fácil identificação', '/images/products/Bola de tênis de mesa.webp'),
('Bola de futebol', '2023-02-28', 9, 0, 50.00, 'ATIVO', 'N/A', 1, 'Bola ideal para futebol de salão e de campo gramado', '/images/products/Bola de futebol.webp'),

('Bola de futebol americano', '2023-02-28', 9, 0, 55.00, 'ATIVO', 'N/A', 1, 'Bola de couro, resistente contra arremessos e impactos', '/images/products/Bola de futebol americano.jpg'),
('Bola de beisebol', '2023-02-28', 9, 0, 32.00, 'ATIVO', 'N/A', 2, 'Bola de couro sintético, resistente contra rebatidas e arremessos por robôs', '/images/products/Bola de beisebol.webp'),
('Bola de basquetebol', '2023-02-28', 9, 0, 45.00, 'ATIVO', 'N/A', 1, 'Bola de alta aderência, ideal para manipular e arremessos', '/images/products/Bola de basquetebol.webp'),
('Barraca de acampamento', '2023-02-28', 9, 0, 270.00, 'ATIVO', 'N/A', 3, 'Barraca para acampamentos em céu aberto', '/images/products/Barraca de acampamento.webp'),
('Anzol de pesca', '2023-02-28', 9, 0, 3.00, 'ATIVO', 'N/A', 1, 'Anzol de aço inoxidável, que pode ser usado em rios e mares', '/images/products/Anzol de pesca.webp');


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
(10,1),

(11,4),
(12,4),
(13,4),
(14,4),
(15,4),
(16,4),
(17,4),
(18,4),
(19,4),
(20,3),
(21,4),
(22,5),
(23,3),
(24,3),
(25,3),
(26,4),
(27,4),
(28,4),
(29,4),
(30,4),
(31,4),
(32,5),
(33,3),
(34,4),
(35,5),
(36,4),
(37,4),
(38,4),
(39,4),
(40,4);
