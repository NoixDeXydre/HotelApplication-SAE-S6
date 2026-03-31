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
import static org.junit.jupiter.api.Assertions.assertSame;

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
}
