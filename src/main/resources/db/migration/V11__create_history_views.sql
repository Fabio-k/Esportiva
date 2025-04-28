CREATE VIEW historico_compras AS
SELECT
    row_number() OVER () as id,
    c.cat_id,
    c.cat_nome,
    pro.pro_id,
    pro.pro_nome_produto,
    t.tra_data_compra,
    p.ped_status,
    SUM(p.ped_quantidade) AS total_de_compras
    FROM transacoes t
    JOIN pedidos p ON t.tra_id = p.ped_tra_id
    JOIN produtos pro ON pro.pro_id = p.ped_pro_id
    JOIN pertence per ON per.per_pro_id = pro.pro_id
    JOIN categorias_produto c ON c.cat_id = per.per_cat_id
    GROUP BY  c.cat_id, c.cat_nome, pro.pro_id, pro.pro_nome_produto, t.tra_data_compra, p.ped_status
    ORDER BY t.tra_data_compra, c.cat_id;