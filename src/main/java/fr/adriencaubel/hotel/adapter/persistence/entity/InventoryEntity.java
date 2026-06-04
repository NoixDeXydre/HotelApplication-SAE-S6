package fr.adriencaubel.hotel.adapter.persistence.entity;

import fr.adriencaubel.hotel.domain.entity.RoomType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table
@Getter
@Setter
public class InventoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private RoomType roomType;

    private LocalDate date;
    private int totalRooms;
    private int reservedRooms;

    public InventoryEntity(long id, RoomType roomType, int reservedRooms, int totalRooms, LocalDate date) {
        this.id = id;
        this.roomType = roomType;
        this.reservedRooms = reservedRooms;
        this.totalRooms = totalRooms;
        this.date = date;
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
