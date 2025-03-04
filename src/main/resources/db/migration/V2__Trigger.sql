-- Define a função que registra na tabela de log
CREATE OR REPLACE FUNCTION registrar_logs() RETURNS TRIGGER AS
$$
BEGIN
    -- TG_OP é uma variável especial do PostgreSQL que registra a operação efetuada
    -- https://www.postgresql.org/docs/current/plpgsql-trigger.html#PLPGSQL-DML-TRIGGER-TG-OP
    IF (TG_OP = 'INSERT') OR (TG_OP = 'UPDATE') THEN
        -- now() -> https://www.postgresql.org/docs/current/functions-datetime.html
        -- row_to_json() -> https://www.postgresql.org/docs/9.5/functions-json.html
        INSERT INTO logs(log_usuario, log_data_hora, log_operacao, log_conteudo_alteracao)
        VALUES (current_user, now(), TG_OP, row_to_json(NEW));
    END IF;
    RETURN NULL;
END;
$$ LANGUAGE plpgsql;


-- Atrela a função aos Triggers em cada tabela alvo

-- Clientes
CREATE TRIGGER log_insert_update_clientes
AFTER INSERT OR UPDATE ON clientes
FOR EACH ROW EXECUTE FUNCTION registrar_logs();

-- Endereços
CREATE TRIGGER log_insert_update_enderecos
AFTER INSERT OR UPDATE ON enderecos
FOR EACH ROW EXECUTE FUNCTION registrar_logs();

-- CEP
CREATE TRIGGER log_insert_update_cep
AFTER INSERT OR UPDATE ON cep
FOR EACH ROW EXECUTE FUNCTION registrar_logs();

-- CEP
CREATE TRIGGER log_insert_update_cartoes_de_credito
AFTER INSERT OR UPDATE ON cartoes_de_credito
FOR EACH ROW EXECUTE FUNCTION registrar_logs();

-- Produtos
CREATE TRIGGER log_insert_update_produtos
AFTER INSERT OR UPDATE ON produtos
FOR EACH ROW EXECUTE FUNCTION registrar_logs();

-- Cupons de promoção
CREATE TRIGGER log_insert_update_cupons_promocao
AFTER INSERT OR UPDATE ON cupons_promocao
FOR EACH ROW EXECUTE FUNCTION registrar_logs();

-- Cupons de troca
CREATE TRIGGER log_insert_update_cupons_troca
AFTER INSERT OR UPDATE ON cupons_troca
FOR EACH ROW EXECUTE FUNCTION registrar_logs();

-- Categorias dos produtos
CREATE TRIGGER log_insert_update_categorias_produto
AFTER INSERT OR UPDATE ON categorias_produto
FOR EACH ROW EXECUTE FUNCTION registrar_logs();

-- Grupo de precificação
CREATE TRIGGER log_insert_update_categorias_grupo_precificacao
AFTER INSERT OR UPDATE ON grupo_precificacao
FOR EACH ROW EXECUTE FUNCTION registrar_logs();

-- Transações
CREATE TRIGGER log_insert_update_categorias_transacoes
AFTER INSERT OR UPDATE ON transacoes
FOR EACH ROW EXECUTE FUNCTION registrar_logs();

-- Pedidos
CREATE TRIGGER log_insert_update_categorias_pedidos
AFTER INSERT OR UPDATE ON pedidos
FOR EACH ROW EXECUTE FUNCTION registrar_logs();
