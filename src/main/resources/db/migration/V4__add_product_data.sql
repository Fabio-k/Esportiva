INSERT INTO categorias_produto (cat_nome) VALUES
('Vôlei'),
('Tênis'),
('Roupas'),
('Acessórios');

INSERT INTO grupo_precificacao (grp_nome, grp_margem_lucro) VALUES
('Baixa', 0.1),
('Média', 0.2),
('Alta', 0.3);

INSERT INTO produtos (
    pro_nome_produto, pro_data_entrada, pro_quantidade_estoque,
    pro_quantidade_bloqueada, pro_margem_lucro, pro_valor_custo,
    pro_categoria_inativacao, pro_justificativa_inativacao, pro_grp_id, pro_descricao, pro_imagem
) VALUES
('Bola de Vôlei Mikasa 350VW', '2024-03-01', 50, 5, 25.0, 120.00, 'ATIVO', 'N/A', 2, 'Perfeita para treinos e competições, a Mikasa 350VW é uma bola oficial de vôlei com tecnologia avançada para melhor aderência e controle. Seu design aerodinâmico e material de alta qualidade garantem jogadas precisas e maior durabilidade. Ideal para atletas e entusiastas do esporte.', '/images/mikasa.png'),
('Tênis Adidas CourtJam', '2024-02-15', 30, 28, 30.0, 250.00, 'ATIVO', 'N/A', 3, 'O Adidas CourtJam combina conforto e desempenho para quem pratica tênis. Com amortecimento responsivo e solado aderente, oferece estabilidade e suporte para movimentos rápidos na quadra. Seu design moderno e respirável proporciona um ajuste confortável e duradouro.', '/images/tenis.png'),
('Camisa Nike Dry Fit', '2024-03-05', 100, 10, 20.0, 80.00, 'ATIVO', 'N/A', 2, 'Desenvolvida para atletas que buscam conforto e desempenho, a camisa Nike Dry Fit é feita com tecido respirável e tecnologia de absorção de suor, mantendo o corpo seco e confortável durante os treinos e competições. Leve, flexível e estilosa, é ideal para qualquer atividade esportiva.', '/images/camiseta.png'),
('Caneleira Puma Pro', '2024-02-20', 40, 4, 15.0, 45.00, 'ATIVO', 'N/A', 1, 'Oferecendo proteção e conforto, a caneleira Puma Pro é essencial para a segurança dos jogadores. Feita com material resistente e design anatômico, proporciona excelente absorção de impacto sem comprometer a mobilidade. Ideal para treinos e jogos intensos.', '/images/caneleta.png');

INSERT INTO pertence (per_pro_id, per_cat_id) VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4);
