package fr.adriencaubel.hotel.domain.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

public class RoomTypePrice {

    private Long id;

    private LocalDate startDate;

    private LocalDate endDate;

    private BigDecimal pricePerNight;

    private RoomType roomType;

    public RoomTypePrice() {
    }

    // getters & setters

    public Long getId() { return id; }

    public LocalDate getStartDate() { return startDate; }

    public LocalDate getEndDate() { return endDate; }

    public BigDecimal getPricePerNight() { return pricePerNight; }

    public RoomType getRoomType() { return roomType; }

    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public void setPricePerNight(BigDecimal pricePerNight) { this.pricePerNight = pricePerNight; }

    public void setRoomType(RoomType roomType) { this.roomType = roomType; }
}
