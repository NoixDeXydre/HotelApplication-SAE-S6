-- liquibase formatted sql

-- changeset creation-invoice:1
CREATE TABLE invoice (
    id                  BIGINT AUTO_INCREMENT PRIMARY KEY,
    invoice_name        VARCHAR(20)    NOT NULL,
    revision            INT            NOT NULL DEFAULT 0,
    type                ENUM('INVOICE', 'CREDIT_NOTE') NOT NULL,
    status              ENUM('DRAFT', 'FINALIZED', 'CANCELLED') NOT NULL DEFAULT 'DRAFT',
    booking_id          BIGINT         NOT NULL,
    amount              DECIMAL(10,2)  NOT NULL,
    issued_at           DATE           NOT NULL,
    client_first_name     VARCHAR(100)   NOT NULL,
    client_last_name      VARCHAR(100)   NOT NULL,
    client_email         VARCHAR(100)   NOT NULL,
    room_type            VARCHAR(100)   NOT NULL,
    date_from            DATE           NOT NULL,
    date_to              DATE           NOT NULL,
    quantity            INT            NOT NULL,

    CONSTRAINT fk_invoice_booking FOREIGN KEY (booking_id) REFERENCES booking(id),
    CONSTRAINT uq_invoice UNIQUE (invoice_name, revision, type)
);

-- GRANT SELECT ON invoice TO 'hotel_user'@'%';

CREATE TRIGGER before_invoice_update
    BEFORE UPDATE ON invoice
    FOR EACH ROW
BEGIN
    SIGNAL SQLSTATE '45000'
    SET MESSAGE_TEXT = 'Invoice is final';
END;

CREATE TRIGGER before_invoice_delete
    BEFORE DELETE ON invoice
    FOR EACH ROW
BEGIN
    SIGNAL SQLSTATE '45000'
    SET MESSAGE_TEXT = 'Invoice is final';
END;
