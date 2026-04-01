package fr.adriencaubel.hotel.apiTest;

import fr.adriencaubel.hotel.api.BookingController;
import fr.adriencaubel.hotel.api.dto.BookingRequest;
import fr.adriencaubel.hotel.domain.Booking;
import fr.adriencaubel.hotel.domain.RoomType;
import fr.adriencaubel.hotel.service.HotelService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BookingControllerTest {

    @Test
    @DisplayName("reserve delegates to HotelService")
    void reserveDelegatesToHotelService() {
        // given
        HotelService hotelService = mock(HotelService.class);
        BookingController controller = new BookingController(hotelService);
        BookingRequest request = new BookingRequest();
        RoomType roomType = new RoomType();
        Booking expected = new Booking(
                "customer@example.com",
                "Doe",
                "John",
                roomType,
                LocalDate.of(2026, 1, 1),
                LocalDate.of(2026, 1, 2),
                1,
                BigDecimal.TEN,
                "CONFIRMED"
        );
        when(hotelService.reserveRoom(request)).thenReturn(expected);

        // when
        Booking actual = controller.reserve(request);

        // then
        assertSame(expected, actual);
    }
}
