package fr.adriencaubel.hotel.adapter.persistence.entity;
import fr.adriencaubel.hotel.domain.BookingOption;
import fr.adriencaubel.hotel.domain.RoomType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Setter
@Getter
public class BookingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne
    public RoomType roomType;
    public LocalDate fromDate;
    public LocalDate toDate;
    public int quantity;
    public BigDecimal amount;
    public String status;
    public String nom;
    public String prenom;
    public String email;

    @OneToMany(mappedBy = "booking",
            cascade = CascadeType.ALL)
    private List<BookingOption> options = new ArrayList<>();

    public BookingEntity(Long id, RoomType roomType, LocalDate fromDate, LocalDate toDate, int quantity, BigDecimal amount) {
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
}

