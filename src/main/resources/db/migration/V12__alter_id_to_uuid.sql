ALTER TABLE produtos
ADD COLUMN pro_uuid UUID;

CREATE EXTENSION IF NOT EXISTS pgcrypto;

update table produtos
set pro_uuid = get_random_uuid()
where pro_uuid IS NULL;

ALTER TABLE produtos
ALTER COLUMN pro_uuid SET NOT NULL;

ALTER TABLE pedidos
ADD COLUMN ped_pro_uuid UUID;

UPDATE pedidos p
SET ped_pro_uuid = pro_uuid
FROM produtos pro
WHERE pro_id = p.ped_pro_id;

ALTER TABLE pedidos
ADD CONSTRAINT fk_ped_pro_new
FOREIGN KEY (ped_pro_uuid)
REFERENCES produtos(pro_uuid);

ALTER TABLE pertence
ADD COLUMN per_pro_uuid UUID;

UPDATE pertence p
SET per_pro_uuid = pro_uuid
FROM produtos pro
WHERE pro_id = p.per_pro_id;

ALTER TABLE pertence
ADD CONSTRAINT fk_per_pro_new
FOREIGN KEY (per_pro_uuid)
REFERENCES produtos(pro_uuid);



