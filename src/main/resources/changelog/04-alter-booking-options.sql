-- liquibase formatted sql

-- changeset alter-booking-options:1
ALTER TABLE booking_options
ADD CONSTRAINT chk_booking_option_type CHECK (type IN ('ANNIVERSAIRE', 'FLEUR', 'LIT_BEBE', 'AUTRE'));