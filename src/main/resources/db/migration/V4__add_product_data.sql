INSERT INTO categorias_produto (cat_nome) VALUES
('Vôlei'),
('Tênis'),
('Roupas'),
('Acessórios');

INSERT INTO grupo_precificacao (grp_nome, grp_margem_lucro) VALUES
('Baixa', 10.0),
('Média', 20.0),
('Alta', 30.0);

INSERT INTO produtos (
    pro_nome_produto, pro_data_entrada, pro_quantidade_estoque,
    pro_quantidade_bloqueada, pro_margem_lucro, pro_valor_custo,
    pro_categoria_inativacao, pro_justificativa_inativacao, pro_grp_id, pro_descricao, pro_imagem
) VALUES
('Bola de Vôlei Mikasa 350VW', '2024-03-01', 50, 5, 25.0, 120.00, 'ATIVO', 'N/A', 2, '', '/images/mikasa.png'),
('Tênis Adidas CourtJam', '2024-02-15', 30, 28, 30.0, 250.00, 'ATIVO', 'N/A', 3, '', '/images/tenis.png'),
('Camisa Nike Dry Fit', '2024-03-05', 100, 10, 20.0, 80.00, 'ATIVO', 'N/A', 2, '', '/images/camiseta.png'),
('Caneleira Puma Pro', '2024-02-20', 40, 4, 15.0, 45.00, 'ATIVO', 'N/A', 1, '', '/images/caneleta.png');

INSERT INTO pertence (per_pro_id, per_cat_id) VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4);
