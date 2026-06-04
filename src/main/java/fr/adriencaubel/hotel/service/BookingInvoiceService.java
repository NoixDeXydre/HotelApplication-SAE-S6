package fr.adriencaubel.hotel.service;

import fr.adriencaubel.hotel.domain.Booking;
import fr.adriencaubel.hotel.domain.Invoice;
import fr.adriencaubel.hotel.domain.InvoiceType;
import fr.adriencaubel.hotel.infra.BookingRepository;
import fr.adriencaubel.hotel.infra.InvoiceRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class BookingInvoiceService {
    private final BookingRepository bookingRepo;
    private final InvoiceRepository invoiceRepo;

    public BookingInvoiceService(BookingRepository bookingRepo, InvoiceRepository invoiceRepo) {
        this.bookingRepo = bookingRepo;
        this.invoiceRepo = invoiceRepo;
    }

    @Transactional
    public void initialInvoice(Long bookingId) {
        Booking booking = bookingRepo.findById(bookingId).orElseThrow();

        Invoice invoice = new Invoice(booking, 0);
        invoice.finalizeInvoice();
        invoiceRepo.save(invoice);
    }

    @Transactional
    public void updateBooking(Long bookingId, BigDecimal newAmount) {
        Booking booking = bookingRepo.findByIdForUpdate(bookingId).orElseThrow();

        if ("PAID".equals(booking.getStatus())) {
            throw new IllegalStateException("Réservation payée : modification impossible");
        }


        Invoice lastInvoice = invoiceRepo
                .findTopByBookingIdAndTypeOrderByRevisionDesc(bookingId, InvoiceType.INVOICE)
                .orElseThrow();

        int revision = lastInvoice.getRevision();
        Invoice creditNote = new Invoice(booking, revision);
        creditNote.toCreditNote();
        invoiceRepo.save(creditNote);

        booking.setAmount(newAmount);
        bookingRepo.save(booking);

        Invoice corrected = new Invoice(booking, revision);
        corrected.revision();
        invoiceRepo.save(corrected);
    }
}
