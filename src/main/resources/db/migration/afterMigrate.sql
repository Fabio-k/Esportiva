-- Inserindo endereços
INSERT INTO address (id, cep, residency_type, street, street_type, number, neighborhood, city, state, country, observation) VALUES
(1, '01001-000', 'APARTMENT', 'avenida faria lima', 'AVENUE', '123', 'Centro', 'São Paulo', 'SP', 'Brasil', 'Próximo ao metrô'),
(2, '22041-001', 'HOUSE', 'rua da liberdade', 'STREET', '456', 'Copacabana', 'Rio de Janeiro', 'RJ', 'Brasil', 'Perto da praia'),
(3, '30130-000', 'APARTMENT', 'praça das flores', 'SQUARE', '789', 'Savassi', 'Belo Horizonte', 'MG', 'Brasil', 'Ao lado do shopping');

-- Inserindo usuários e associando aos endereços
INSERT INTO users (id, role, name, email, password, code, registration_number, status, gender, address_id) VALUES
(1, 'ADMIN', 'Carlos Silva', 'carlos@email.com', 'senha123', 'USR001', '2024001', 'ACTIVE', 'MALE', 1),
(2, 'USER', 'Mariana Souza', 'mariana@email.com', 'senha123', 'USR002', '2024002', 'ACTIVE', 'FEMALE', 2),
(3, 'USER', 'Pedro Oliveira', 'pedro@email.com', 'senha123', 'USR003', '2024003', 'INACTIVE', 'MALE', 3);

ALTER TABLE address ALTER COLUMN id RESTART WITH 4;
ALTER TABLE users ALTER COLUMN id RESTART WITH 4;
