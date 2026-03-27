package fr.adriencaubel.hotel.infraTest;

import fr.adriencaubel.hotel.domain.Booking;
import fr.adriencaubel.hotel.infra.BookingRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BookingRepositoryTest {

    @Test
    @DisplayName("save can be stubbed on repository")
    void saveCanBeStubbedOnRepository() {
        // given
        BookingRepository repository = mock(BookingRepository.class);
        Booking booking = new Booking();
        when(repository.save(booking)).thenReturn(booking);

        // when
        Booking result = repository.save(booking);

        // then
        assertSame(booking, result);
    }
}
