package fr.adriencaubel.hotel.domain;

public class BookingOption {

    private Long id;

    private String type; // ANNIVERSAIRE, FLEUR, LIT BEBE, AUTRE

    private String comment; // texte libre

    private Booking booking;

    public BookingOption() {
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
