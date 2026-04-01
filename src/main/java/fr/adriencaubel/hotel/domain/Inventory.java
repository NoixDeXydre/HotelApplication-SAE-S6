package fr.adriencaubel.hotel.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table
@Getter
@Setter
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private RoomType roomType;

    private LocalDate date;
    private int totalRooms;
    private int reservedRooms;

    public Inventory(RoomType roomType, LocalDate date, int totalRooms) {
        if (roomType == null) {
            throw new IllegalArgumentException("RoomType is not existing");
        }

        this.roomType = roomType;
        this.date = date;
        this.totalRooms = totalRooms;
        this.reservedRooms = 0;
    }

    public boolean canReserve(int quantity) {
        return availableRooms() >= quantity;
    }

    public void reserve(int quantity) {
        if (!canReserve(quantity)) {
            throw new IllegalStateException(
                    "Not enough rooms available for " + date
            );
        }

        reservedRooms += quantity;
    }

    public void release(int quantity) {
        if (quantity > reservedRooms) {
            throw new IllegalStateException("Cannot release more than reserved");
        }

        reservedRooms -= quantity;
    }

    public int availableRooms() {
        return totalRooms - reservedRooms;
    }

    public LocalDate getDate() {
        return date;
    }

    public void addReservedRooms(int quantity) {
        reservedRooms += quantity;
    }
}
