-- liquibase formatted sql

-- changeset creation-customer:1
CREATE TABLE customer (
    id BIGINT PRIMARY KEY,
    last_name VARCHAR(255) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL
);