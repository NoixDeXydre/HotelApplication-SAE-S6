package fr.adriencaubel.hotel.apiTest;

import fr.adriencaubel.hotel.api.BookingController;
import fr.adriencaubel.hotel.api.dto.BookingRequest;
import fr.adriencaubel.hotel.domain.Booking;
import fr.adriencaubel.hotel.service.HotelService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
        Booking expected = new Booking();
        when(hotelService.reserveRoom(request)).thenReturn(expected);

        // when
        Booking actual = controller.reserve(request);

        // then
        assertSame(expected, actual);
    }
}
