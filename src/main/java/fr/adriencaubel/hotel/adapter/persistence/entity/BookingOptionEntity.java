package fr.adriencaubel.hotel.adapter.persistence.entity;


import fr.adriencaubel.hotel.domain.Booking;
import jakarta.persistence.*;

@Entity
@Table(name = "booking_options")
public class BookingOptionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type; // ANNIVERSAIRE, FLEUR, LIT BEBE, AUTRE

    private String comment; // texte libre

    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;

    public BookingOptionEntity() {
    }

    public BookingOptionEntity(String type, String comment) {
        this.type = type;
        this.comment = comment;
    }

    public BookingOptionEntity(Long id, Booking booking, String comment, String type) {
        this.id = id;
        this.booking = booking;
        this.comment = comment;
        this.type = type;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getId() {
        return id;
    }
}
