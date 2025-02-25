CREATE TABLE ADDRESS (
    id BIGSERIAL PRIMARY KEY,
    name varchar(30),
    cep varchar(9) NOT NULL,
    street_type varchar(20) NOT NULL,
    residency_type varchar(20) NOT NULL,
    number varchar(10) NOT NULL,
    neighborhood varchar(30) NOT NULL,
    street varchar(30) NOT NULL,
    city varchar(30) NOT NULL,
    state varchar(30) NOT NULL,
    country varchar(30) NOT NULL,
    observation varchar(200)
)