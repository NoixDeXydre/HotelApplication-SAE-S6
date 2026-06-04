package fr.adriencaubel.hotel.domainTest;

import fr.adriencaubel.hotel.domain.Booking;
import fr.adriencaubel.hotel.domain.BookingOption;
import fr.adriencaubel.hotel.domain.RoomType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BookingTest {

    @Test
    @DisplayName("setOptions and getOptions work as expected")
    void setOptionsAndGetOptionsWorkAsExpected() {
        // given
        Booking booking = new Booking(
                "customer@example.com",
                "Doe",
                "John",
                new RoomType(),
                LocalDate.of(2026, 1, 1),
                LocalDate.of(2026, 1, 2),
                1,
                BigDecimal.TEN,
                "CONFIRMED"
        );
        BookingOption option = new BookingOption("FLEUR", "rose");
        List<BookingOption> options = List.of(option);

        // when
        booking.setOptions(options);
        List<BookingOption> result = booking.getOptions();

        // then
        assertEquals(1, result.size());
        assertSame(option, result.get(0));
    }

    @Test
    @DisplayName("full constructor sets all fields correctly")
    void fullConstructorSetsAllFieldsCorrectly() {
        // given
        RoomType roomType = new RoomType();
        LocalDate from = LocalDate.of(2026, 6, 1);
        LocalDate to = LocalDate.of(2026, 6, 5);

        // when
        Booking booking = new Booking(
                "jane@example.com",
                "Jane",
                "Smith",
                roomType,
                from,
                to,
                3,
                new BigDecimal("300.00"),
                "CONFIRMED"
        );

        // then
        assertEquals("jane@example.com", booking.getEmail());
        assertEquals("Jane", booking.getNom());
        assertEquals("Smith", booking.getPrenom());
        assertSame(roomType, booking.getRoomType());
        assertEquals(from, booking.getFromDate());
        assertEquals(to, booking.getToDate());
        assertEquals(3, booking.getQuantity());
        assertEquals(new BigDecimal("300.00"), booking.getAmount());
        assertEquals("CONFIRMED", booking.getStatus());
    }

    @Test
    @DisplayName("default constructor creates booking with empty options list")
    void defaultConstructorCreatesBookingWithEmptyOptionsList() {
        // when
        Booking booking = new Booking();

        // then
        assertNotNull(booking.getOptions());
        assertTrue(booking.getOptions().isEmpty());
    }

    @Test
    @DisplayName("addOption appends option to list")
    void addOptionAppendsOptionToList() {
        // given
        Booking booking = new Booking(
                "customer@example.com",
                "Doe",
                "John",
                new RoomType(),
                LocalDate.of(2026, 1, 1),
                LocalDate.of(2026, 1, 3),
                1,
                BigDecimal.TEN,
                "CONFIRMED"
        );
        BookingOption option1 = new BookingOption("FLEUR", "rose");
        BookingOption option2 = new BookingOption("ANNIVERSAIRE", "gateau");

        // when
        booking.addOption(option1);
        booking.addOption(option2);

        // then
        assertEquals(2, booking.getOptions().size());
        assertSame(option1, booking.getOptions().get(0));
        assertSame(option2, booking.getOptions().get(1));
    }

    @Test
    @DisplayName("setters update scalar fields")
    void settersUpdateScalarFields() {
        // given
        Booking booking = new Booking();

        // when
        booking.setId(42L);
        booking.setStatus("CANCELLED");
        booking.setAmount(new BigDecimal("99.00"));
        booking.setEmail("updated@example.com");
        booking.setNom("UpdatedNom");
        booking.setPrenom("UpdatedPrenom");
        booking.setQuantity(5);

        // then
        assertEquals(42L, booking.getId());
        assertEquals("CANCELLED", booking.getStatus());
        assertEquals(new BigDecimal("99.00"), booking.getAmount());
        assertEquals("updated@example.com", booking.getEmail());
        assertEquals("UpdatedNom", booking.getNom());
        assertEquals("UpdatedPrenom", booking.getPrenom());
        assertEquals(5, booking.getQuantity());
    }
}
