INSERT INTO transacoes (tra_data_compra, tra_status, tra_cli_id) VALUES
('2024-03-07', 'EM_PROCESSAMENTO', '1'),
('2024-04-07', 'EM_TRANSITO', '1'),
('2024-05-07', 'ENTREGUE', '1'),
('2024-06-07', 'ENTREGUE', '1'),
('2024-07-07', 'ENTREGUE', '1'),
('2024-12-25', 'EM_PROCESSAMENTO', '2'),
('2024-12-30', 'EM_PROCESSAMENTO', '2'),
('2025-01-12', 'ENTREGUE', '3');


INSERT INTO pedidos (ped_status, ped_quantidade, ped_tra_id, ped_pro_id) VALUES
('EM_PROCESSAMENTO', '2', '1', '1'),
('EM_PROCESSAMENTO', '1', '1', '2'),
('EM_PROCESSAMENTO', '7', '1', '3'),
('EM_TRANSITO', '3', '2', '5'),
('EM_TRANSITO', '1', '3', '6'),
('ENTREGUE', '3', '3', '1'),
('EM_TROCA', '2', '4', '1'),
('TROCADO', '2', '5', '6'),
('EM_PROCESSAMENTO', '10', '6', '5'),
('EM_PROCESSAMENTO', '5', '7', '1'),
('EM_PROCESSAMENTO', '5', '7', '9'),
('EM_TROCA', '1', '8', '9');