package fr.adriencaubel.hotel.domain.entity;

import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Booking {
    public Long id;

    public RoomType roomType;
    public LocalDate fromDate;
    public LocalDate toDate;
    public int quantity;
    public BigDecimal amount;
    public String status;
    public String nom;
    public String prenom;
    public String email;

    @Setter
    private List<BookingOption> options = new ArrayList<>();

    public Booking(Long id, RoomType roomType, LocalDate fromDate, LocalDate toDate, int quantity, BigDecimal amount) {
        this.id = id;
        this.roomType = roomType;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.quantity = quantity;
        this.amount = amount;
    }

    public List<BookingOption> getOptions() {
        return options;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getStatus() {
        return status;
    }

    public String getEmail() {
        return email;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }
    public LocalDate getToDate() {
        return toDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public Long getId() {
        return id;
    }

    public RoomType getRoomType() {
        return roomType;
    }
}
