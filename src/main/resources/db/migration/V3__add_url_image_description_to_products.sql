ALTER table produtos
ADD COLUMN pro_descricao VARCHAR(500),
ADD COLUMN pro_imagem VARCHAR(400),
ALTER COLUMN pro_valor_custo TYPE NUMERIC(10,2);