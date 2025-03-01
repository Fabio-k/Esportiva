-- Inserindo usuários e associando aos endereços
INSERT INTO users (role, name, email, code, registration_number, status, gender) VALUES
('ADMIN', 'Carlos Silva', 'carlos@email.com', 'USR001', '2024001', 'ACTIVE', 'MALE'),
('USER', 'Mariana Souza', 'mariana@email.com', 'USR002', '2024002', 'ACTIVE', 'FEMALE'),
('USER', 'Pedro Oliveira', 'pedro@email.com', 'USR003', '2024003', 'INACTIVE', 'MALE');

-- Inserindo endereços
INSERT INTO address (cep, residency_type, street, street_type, number, neighborhood, city, state, country, observation, user_id) VALUES
('01001-000', 'APARTMENT', 'avenida faria lima', 'AVENUE', '123', 'Centro', 'São Paulo', 'SP', 'Brasil', 'Próximo ao metrô', 1),
('22041-001', 'HOUSE', 'rua da liberdade', 'STREET', '456', 'Copacabana', 'Rio de Janeiro', 'RJ', 'Brasil', 'Perto da praia', 2),
('30130-000', 'APARTMENT', 'praça das flores', 'SQUARE', '789', 'Savassi', 'Belo Horizonte', 'MG', 'Brasil', 'Ao lado do shopping', 3);

-- Inserindo tipos de endereço
INSERT INTO address_type (type, address_id) VALUES
('RESIDENCE', 1),
('RESIDENCE', 2),
('RESIDENCE', 3);
