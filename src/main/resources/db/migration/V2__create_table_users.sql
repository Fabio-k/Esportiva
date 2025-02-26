CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    role VARCHAR(50),
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    code VARCHAR(100) UNIQUE NOT NULL,
    registration_number VARCHAR(100) NOT NULL,
    status VARCHAR(50) NOT NULL,
    gender VARCHAR(50) NOT NULL,
    address_id BIGINT NOT NULL UNIQUE,
    CONSTRAINT fk_users_address FOREIGN KEY (address_id) REFERENCES address(id)
);