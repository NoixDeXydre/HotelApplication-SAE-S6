package fr.adriencaubel.hotel.domain;


import jakarta.persistence.*;

@Entity
@Table(name = "booking_options")
public class BookingOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type; // ANNIVERSAIRE, FLEUR, LIT BEBE, AUTRE

    private String comment; // texte libre

    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;

    public BookingOption(String[] parts) {
        if (parts.length < 1) {
            throw new IllegalArgumentException(
                    "Invalid option format. Expected 'type,comment'"
            );
        }

        this.type = parts[0].trim();

        this.comment = null;
        if (parts.length == 2) {
            comment = parts[1].trim();
        }
    }

    public BookingOption(String type, String comment) {
        this.type = type;
        this.comment = comment;
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
