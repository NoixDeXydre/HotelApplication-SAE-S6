package fr.adriencaubel.hotel.domainTest;

import fr.adriencaubel.hotel.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InvoiceTest {

    private Booking booking;
    private RoomType roomType;

    @BeforeEach
    void setUp() {
        roomType = mock(RoomType.class);
        when(roomType.getName()).thenReturn("Room Deluxe");

        booking = mock(Booking.class);
        when(booking.getId()).thenReturn(123L);
        when(booking.getAmount()).thenReturn(new BigDecimal("500.00"));
        when(booking.getPrenom()).thenReturn("Willy");
        when(booking.getNom()).thenReturn("Wonka");
        when(booking.getEmail()).thenReturn("willy@example.com");
        when(booking.getRoomType()).thenReturn(roomType);
        when(booking.getFromDate()).thenReturn(LocalDate.of(2026, 6, 1));
        when(booking.getToDate()).thenReturn(LocalDate.of(2026, 6, 5));
        when(booking.getQuantity()).thenReturn(1);
    }

    @Test
    @DisplayName("Constructor should correctly initialize invoice from booking")
    void constructorShouldInitializeCorrectly() {
        // given
        int revision = 1;

        // when
        Invoice invoice = new Invoice(booking, revision);

        // then
        assertEquals("Fact123", invoice.getInvoiceName());
        assertEquals(InvoiceType.INVOICE, invoice.getType());
        assertEquals(InvoiceStatus.DRAFT, invoice.getStatus());
        assertEquals(new BigDecimal("500.00"), invoice.getAmount());
        assertEquals("Willy", invoice.getClient_first_name());
        assertEquals("Wonka", invoice.getClient_last_name());
        assertEquals("willy@example.com", invoice.getClient_email());
        assertEquals("Room Deluxe", invoice.getRoom_type());
        assertEquals(revision, invoice.getRevision());
        assertEquals(LocalDate.now(), invoice.getIssuedAt());
    }

    @Test
    @DisplayName("toCreditNote should negate amount and change type")
    void toCreditNoteShouldUpdateInvoice() {
        // given
        Invoice invoice = new Invoice(booking, 0);

        // when
        invoice.toCreditNote();

        // then
        assertEquals(new BigDecimal("-500.00"), invoice.getAmount());
        assertEquals(InvoiceType.CREDIT_NOTE, invoice.getType());
    }

    @Test
    @DisplayName("cancelInvoice should set status to CANCELLED")
    void cancelInvoiceShouldUpdateStatus() {
        // given
        Invoice invoice = new Invoice(booking, 0);

        // when
        invoice.cancelInvoice();

        // then
        assertEquals(InvoiceStatus.CANCELLED, invoice.getStatus());
    }

    @Test
    @DisplayName("finalizeInvoice should change DRAFT to FINALIZED")
    void finalizeInvoiceShouldWorkWhenDraft() {
        // given
        Invoice invoice = new Invoice(booking, 0);

        // when
        invoice.finalizeInvoice();

        // then
        assertEquals(InvoiceStatus.FINALIZED, invoice.getStatus());
    }

    @Test
    @DisplayName("finalizeInvoice should throw exception if not DRAFT")
    void finalizeInvoiceShouldThrowExceptionIfNotDraft() {
        // given
        Invoice invoice = new Invoice(booking, 0);
        invoice.cancelInvoice(); // Status is now CANCELLED

        // when & then
        IllegalStateException exception = assertThrows(IllegalStateException.class, invoice::finalizeInvoice);
        assertEquals("Unable to finalize the invoice", exception.getMessage());
    }

    @Test
    @DisplayName("revision should increment version and finalize invoice")
    void revisionShouldIncrementAndFinalize() {
        // given
        Invoice invoice = new Invoice(booking, 1);

        // when
        invoice.revision();

        // then
        assertEquals(2, invoice.getRevision());
        assertEquals(InvoiceStatus.FINALIZED, invoice.getStatus());
    }
}