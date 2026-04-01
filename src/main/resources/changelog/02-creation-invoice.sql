-- liquibase formatted sql

-- changeset creation-invoice:1
CREATE TABLE invoice (
    id BIGINT PRIMARY KEY,
    price DECIMAL(4, 2) NOT NULL,
    date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    id_booking BIGINT NOT NULL,
    CONSTRAINT fk_invoice_booking FOREIGN KEY (id_booking) REFERENCES booking(id)
);