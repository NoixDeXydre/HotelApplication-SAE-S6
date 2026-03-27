package fr.adriencaubel.hotel.serviceTest;

import fr.adriencaubel.hotel.service.CustomerDto;
import fr.adriencaubel.hotel.service.CustomerValidationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CustomerValidationServiceTest {

    @Test
    @DisplayName("splitCustomer parses valid input")
    void splitCustomerParsesValidInput() {
        // given
        String input = "Jean Dupont jean.dupont@example.com";

        // when
        CustomerDto dto = CustomerValidationService.splitCustomer(input);

        // then
        assertEquals("Jean", dto.getFirstName());
        assertEquals("Dupont", dto.getLastName());
        assertEquals("jean.dupont@example.com", dto.getEmail());
    }

    @Test
    @DisplayName("splitCustomer rejects invalid format")
    void splitCustomerRejectsInvalidFormat() {
        // given
        String input = "Jean Dupont";

        // when
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> CustomerValidationService.splitCustomer(input)
        );

        // then
        assertEquals("Invalid format", ex.getMessage());
    }

    @Test
    @DisplayName("splitCustomer rejects invalid email")
    void splitCustomerRejectsInvalidEmail() {
        // given
        String input = "Jean Dupont jean.dupont.example.com";

        // when
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> CustomerValidationService.splitCustomer(input)
        );

        // then
        assertEquals("Invalid email", ex.getMessage());
    }
}
