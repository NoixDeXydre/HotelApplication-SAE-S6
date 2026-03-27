package fr.adriencaubel.hotel.apiTest;

import fr.adriencaubel.hotel.api.AvailabilityController;
import fr.adriencaubel.hotel.api.dto.AvailabilityResponse;
import fr.adriencaubel.hotel.service.HotelService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AvailabilityControllerTest {

    @Test
    @DisplayName("check delegates to HotelService")
    void checkDelegatesToHotelService() {
        // given
        HotelService hotelService = mock(HotelService.class);
        AvailabilityController controller = new AvailabilityController(hotelService);
        LocalDate from = LocalDate.of(2024, 1, 10);
        LocalDate to = LocalDate.of(2024, 1, 12);
        AvailabilityResponse expected = new AvailabilityResponse(1L, from, to, true, 2);
        when(hotelService.checkAvailability(1L, from, to, 2)).thenReturn(expected);

        // when
        AvailabilityResponse actual = controller.check(1L, from, to, 2);

        // then
        assertSame(expected, actual);
    }
}
