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
    private LocalDate date;

    private String clientFirstName;
    private String clientLastName;
    private String clientEmail;

    private String roomType;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private int quantity;

    @Enumerated(EnumType.STRING)
    private InvoiceStatus status;

    private Invoice() {}

    public Invoice(Booking booking) {
        this.booking = booking;
        this.invoiceName = "Fact" + this.booking.getId();
        this.type = InvoiceType.INVOICE;

        this.amount = booking.getAmount();
        this.date = LocalDate.now();

        this.clientFirstName = booking.getPrenom();
        this.clientLastName =  booking.getNom();
        this.clientEmail =  booking.getEmail();

        this.roomType = booking.getRoomType().getName();
        this.dateFrom = booking.getFromDate();
        this.dateTo = booking.getToDate();
        this.quantity = booking.getQuantity();
    }

    public boolean modifFacture(BigDecimal amount, LocalDate dateFrom, LocalDate dateTo) {
        if (status != InvoiceStatus.DRAFT) {
            return false;
        }
        if (amount != null) {
            this.amount = amount;
            this.dateFrom = dateFrom;
            this.dateTo = dateTo;
        } else {
            return false;
        }
        return true;
    }

    public void cancelInvoice() {
        this.status = InvoiceStatus.CANCELLED;
    }

    public void finalizeInvoice() {
        if (status != InvoiceStatus.DRAFT) {
            throw new IllegalStateException("Unable to finalize the invoice");
        }
        this.status = InvoiceStatus.FINISHED;
    }
}
