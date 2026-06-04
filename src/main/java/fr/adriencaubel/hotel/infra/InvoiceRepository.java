package fr.adriencaubel.hotel.infra;

import fr.adriencaubel.hotel.domain.Invoice;
import fr.adriencaubel.hotel.domain.InvoiceType;

import java.util.Optional;

public interface InvoiceRepository {
    void save(Invoice invoice);

    Optional<Invoice> findTopByBookingIdAndTypeOrderByRevisionDesc(Long bookingId, InvoiceType invoiceType);
}
