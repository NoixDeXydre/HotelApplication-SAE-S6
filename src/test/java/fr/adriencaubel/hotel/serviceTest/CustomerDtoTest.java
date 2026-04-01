package fr.adriencaubel.hotel.serviceTest;

import fr.adriencaubel.hotel.service.CustomerDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomerDtoTest {

    @Test
    @DisplayName("constructor and getters return expected values")
    void constructorAndGettersReturnExpectedValues() {
        // given
        CustomerDto dto = new CustomerDto("Jean", "Dupont", "jean@example.com");

        // when
        String firstName = dto.getFirstName();
        String lastName = dto.getLastName();
        String email = dto.getEmail();

        // then
        assertEquals("Jean", firstName);
        assertEquals("Dupont", lastName);
        assertEquals("jean@example.com", email);
    }

    @Test
    @DisplayName("setters update values")
    void settersUpdateValues() {
        // given
        CustomerDto dto = new CustomerDto("Jean", "Dupont", "jean@example.com");

        // when
        dto.setFirstName("Marie");
        dto.setLastName("Curie");
        dto.setEmail("marie@example.com");

        // then
        assertEquals("Marie", dto.getFirstName());
        assertEquals("Curie", dto.getLastName());
        assertEquals("marie@example.com", dto.getEmail());
    }
}
