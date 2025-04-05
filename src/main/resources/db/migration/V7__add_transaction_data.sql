INSERT INTO transacoes (tra_data_compra, tra_status, tra_cli_id) VALUES
(CURRENT_DATE - INTERVAL '5 days', 'EM_TRANSITO', 1),
(CURRENT_DATE - INTERVAL '7 days', 'EM_TRANSITO', 1),
(CURRENT_DATE - INTERVAL '15 days', 'ENTREGUE', 1);

INSERT INTO pedidos (ped_status, ped_quantidade, ped_tra_id, ped_pro_id) VALUES
('EM_TRANSITO', 2, 1, 1),
('EM_TRANSITO', 4, 1, 2),
('EM_TRANSITO', 2, 2, 3),
('EM_TRANSITO', 1, 2, 4),
('ENTREGUE', 2, 3, 1),
('ENTREGUE', 4, 3, 2),
('ENTREGUE', 2, 3, 3),
('ENTREGUE', 4, 3, 4);
