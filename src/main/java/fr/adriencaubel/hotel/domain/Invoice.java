package fr.adriencaubel.hotel.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table
@Getter
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String invoiceName;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;

    @Enumerated(EnumType.STRING)
    private InvoiceType type;

    private BigDecimal amount;

    private String client_first_name;
    private String client_last_name;
    private String client_email;

    private String room_type;
    private LocalDate date_from;
    private LocalDate date_to;
    private int quantity;

    @Enumerated(EnumType.STRING)
    private InvoiceStatus status;

    private Invoice() {}

    public Invoice(Booking booking) {
        this.booking = booking;
        this.invoiceName = "Fact" + this.booking.getId();
        this.type = InvoiceType.INVOICE;

        this.amount = booking.getAmount();

        this.client_first_name = booking.getPrenom();
        this.client_last_name =  booking.getNom();
        this.client_email =  booking.getEmail();

        this.room_type = booking.getRoomType().getName();
        this.date_from = booking.getFromDate();
        this.date_to = booking.getToDate();
        this.quantity = booking.getQuantity();
    }

    public void toCreditNote() {
        this.amount = this.amount.negate();
        this.type = InvoiceType.CREDIT_NOTE;
    }

    public void cancelInvoice() {
        this.status = InvoiceStatus.CANCELLED;
    }

    public void finalizeInvoice() {
        if (status != InvoiceStatus.DRAFT) {
            throw new IllegalStateException("Unable to finalize the invoice");
        }
        this.status = InvoiceStatus.FINALIZED;
    }
}
