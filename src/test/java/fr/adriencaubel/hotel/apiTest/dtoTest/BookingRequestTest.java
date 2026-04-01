package fr.adriencaubel.hotel.apiTest.dtoTest;

import fr.adriencaubel.hotel.api.dto.BookingRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class BookingRequestTest {

    @Test
    @DisplayName("default values are set")
    void defaultValuesAreSet() {
        // given
        BookingRequest request = new BookingRequest();

        // when
        int quantity = request.quantity;
        List<String> options = request.options;

        // then
        assertEquals(1, quantity);
        assertNotNull(options);
        assertEquals(0, options.size());
    }

    @Test
    @DisplayName("fields can be assigned")
    void fieldsCanBeAssigned() {
        // given
        BookingRequest request = new BookingRequest();

        // when
        request.roomTypeId = 7L;
        request.from = LocalDate.of(2024, 2, 10);
        request.to = LocalDate.of(2024, 2, 12);
        request.nomPrenomEmail = "Jean Dupont jean.dupont@example.com";
        request.amount = new BigDecimal("200.00");
        request.quantity = 2;
        request.options = List.of("ANNIVERSAIRE,Gateau");

        // then
        assertEquals(7L, request.roomTypeId);
        assertEquals(LocalDate.of(2024, 2, 10), request.from);
        assertEquals(LocalDate.of(2024, 2, 12), request.to);
        assertEquals("Jean Dupont jean.dupont@example.com", request.nomPrenomEmail);
        assertEquals(new BigDecimal("200.00"), request.amount);
        assertEquals(2, request.quantity);
        assertEquals(List.of("ANNIVERSAIRE,Gateau"), request.options);
    }
}
