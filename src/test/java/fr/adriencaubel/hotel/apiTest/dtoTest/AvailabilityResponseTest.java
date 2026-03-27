package fr.adriencaubel.hotel.apiTest.dtoTest;

import fr.adriencaubel.hotel.api.dto.AvailabilityResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AvailabilityResponseTest {

    @Test
    @DisplayName("constructor sets all fields")
    void constructorSetsAllFields() {
        // given
        LocalDate from = LocalDate.of(2024, 1, 1);
        LocalDate to = LocalDate.of(2024, 1, 2);

        // when
        AvailabilityResponse response = new AvailabilityResponse(5L, from, to, true, 3);

        // then
        assertEquals(5L, response.roomTypeId);
        assertEquals(from, response.from);
        assertEquals(to, response.to);
        assertEquals(true, response.available);
        assertEquals(3, response.remainingMin);
    }
}
