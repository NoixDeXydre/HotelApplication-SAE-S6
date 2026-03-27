package fr.adriencaubel.hotel.domainTest;

import fr.adriencaubel.hotel.domain.Booking;
import fr.adriencaubel.hotel.domain.BookingOption;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

class BookingOptionTest {

    @Test
    @DisplayName("constructors set fields")
    void constructorsSetFields() {
        // given
        BookingOption option = new BookingOption("ANNIVERSAIRE", "gateau");

        // when
        String type = option.getType();
        String comment = option.getComment();

        // then
        assertEquals("ANNIVERSAIRE", type);
        assertEquals("gateau", comment);
    }

    @Test
    @DisplayName("setters update fields")
    void settersUpdateFields() {
        // given
        BookingOption option = new BookingOption();
        Booking booking = new Booking();

        // when
        option.setType("FLEUR");
        option.setComment("rose");
        option.setBooking(booking);

        // then
        assertEquals("FLEUR", option.getType());
        assertEquals("rose", option.getComment());
        assertSame(booking, option.getBooking());
    }

    @Test
    @DisplayName("getId returns null when not persisted")
    void getIdReturnsNullWhenNotPersisted() {
        // given
        BookingOption option = new BookingOption();

        // when
        Long id = option.getId();

        // then
        assertNull(id);
    }
}
