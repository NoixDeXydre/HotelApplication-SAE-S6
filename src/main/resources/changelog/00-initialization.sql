-- liquibase formatted sql

-- changeset initialization:1
CREATE TABLE room_type (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    total_rooms INT NOT NULL
);

-- changeset initialization:2
CREATE TABLE room_type_prices (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    start_date DATE,
    end_date DATE,
    price_per_night DECIMAL(19, 2),
    room_type_id BIGINT,
    CONSTRAINT fk_room_type_prices_room FOREIGN KEY (room_type_id) REFERENCES room_type(id)
);

-- changeset initialization:3
CREATE TABLE inventory (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    date DATE,
    total_rooms INT NOT NULL,
    reserved_rooms INT NOT NULL,
    room_type_id BIGINT,
    CONSTRAINT fk_inventory_room FOREIGN KEY (room_type_id) REFERENCES room_type(id)
);

-- changeset initialization:4
CREATE TABLE booking (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    from_date DATE,
    to_date DATE,
    quantity INT NOT NULL,
    amount DECIMAL(19, 2),
    status VARCHAR(255),
    nom VARCHAR(255),
    prenom VARCHAR(255),
    email VARCHAR(255),
    room_type_id BIGINT,
    CONSTRAINT fk_booking_room FOREIGN KEY (room_type_id) REFERENCES room_type(id)
);

-- changeset initialization:5
CREATE TABLE booking_options (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(255),
    comment VARCHAR(255),
    booking_id BIGINT,
    CONSTRAINT fk_options_booking FOREIGN KEY (booking_id) REFERENCES booking(id)
);