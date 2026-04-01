package fr.adriencaubel.hotel.apiTest.dtoTest;

import fr.adriencaubel.hotel.api.dto.BookingResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BookingResponseTest {

    @Test
    @DisplayName("constructor sets all fields")
    void constructorSetsAllFields() {
        // given
        UUID id = UUID.randomUUID();

        // when
        BookingResponse response = new BookingResponse(id, "CONFIRMED", "ok");

        // then
        assertEquals(id, response.bookingId);
        assertEquals("CONFIRMED", response.status);
        assertEquals("ok", response.message);
    }
}
