package fr.adriencaubel.hotel.infraTest;

import fr.adriencaubel.hotel.domain.Booking;
import fr.adriencaubel.hotel.domain.RoomType;
import fr.adriencaubel.hotel.infra.BookingRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BookingRepositoryTest {

    @Test
    @DisplayName("save can be stubbed on repository")
    void saveCanBeStubbedOnRepository() {
        // given
        BookingRepository repository = mock(BookingRepository.class);
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
        when(repository.save(booking)).thenReturn(booking);

        // when
        Booking result = repository.save(booking);

        // then
        assertSame(booking, result);
    }
}
