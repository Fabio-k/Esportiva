CREATE TABLE USER_ADDRESS (
    id BIGSERIAL PRIMARY KEY,

    address_type VARCHAR NOT NULL,

    user_id BIGINT NOT NULL,
    address_id BIGINT NOT NULL,

    CONSTRAINT fk_UAR_user FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_UAR_address FOREIGN KEY (address_id) REFERENCES address(id)
);