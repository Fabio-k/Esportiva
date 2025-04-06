CREATE TABLE cupons_promocionais (
    cpp_id SERIAL PRIMARY KEY,
    cpp_codigo VARCHAR(30) NOT NULL,
    cpp_desconto INTEGER NOT NULL
);

INSERT INTO cupons_promocionais (cpp_codigo, cpp_desconto) VALUES
('ESPORTIVA10', 10),
('ESPORTIVA15', 15);