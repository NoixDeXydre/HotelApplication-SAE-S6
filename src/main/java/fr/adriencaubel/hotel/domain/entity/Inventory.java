package fr.adriencaubel.hotel.domain.entity;

import java.time.LocalDate;

public class Inventory {

    private Long id;

    private RoomType roomType;

    private LocalDate date;
    private int totalRooms;
    private int reservedRooms;

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

    public int getTotalRooms() {
        return totalRooms;
    }

    public int getReservedRooms() {
        return reservedRooms;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setTotalRooms(int totalRooms) {
        this.totalRooms = totalRooms;
    }

    public void setReservedRooms(int reservedRooms) {
        this.reservedRooms = reservedRooms;
    }
}
