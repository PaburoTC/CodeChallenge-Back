CREATE TABLE address (
    id SERIAL PRIMARY  KEY,
    street VARCHAR NOT NULL,
    state VARCHAR NOT NULL,
    city VARCHAR NOT NULL,
    country VARCHAR NOT NULL,
    zip VARCHAR NOT NULL
);

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL,
    email VARCHAR NOT NULL,
    birthDate DATE NOT NULL,
    address_id INTEGER REFERENCES address(id)
);