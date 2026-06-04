package fr.adriencaubel.hotel.infra;

import fr.adriencaubel.hotel.domain.Invoice;
import fr.adriencaubel.hotel.domain.InvoiceType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    Optional<Invoice> findTopByBookingIdAndTypeOrderByRevisionDesc(Long bookingId, InvoiceType invoiceType);
}
