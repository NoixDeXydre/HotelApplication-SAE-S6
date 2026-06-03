-- liquibase formatted sql

-- changeset alter-booking:1
-- Sécurisation du statut : on ajoute la contrainte CHECK sur la colonne existante
ALTER TABLE booking
ADD CONSTRAINT chk_booking_status CHECK (status IN ('PENDING', 'CONFIRMED', 'PAID', 'CANCELLED'));

-- changeset alter-booking:2
-- Lien entre Booking et Customer
ALTER TABLE booking ADD COLUMN customer_id BIGINT;

-- changeset alter-booking:3
ALTER TABLE booking ADD CONSTRAINT fk_booking_customer FOREIGN KEY (customer_id) REFERENCES customer(id);