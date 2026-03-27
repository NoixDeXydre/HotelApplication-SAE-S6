package fr.adriencaubel.hotelTest;

import fr.adriencaubel.hotel.HotelApplication;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.assertTrue;

class HotelApplicationTest {

    @Test
    @DisplayName("main method exists and is static")
    void mainMethodExistsAndIsStatic() throws Exception {
        // given
        Method main = HotelApplication.class.getMethod("main", String[].class);

        // when
        int modifiers = main.getModifiers();

        // then
        assertTrue(Modifier.isStatic(modifiers));
    }
}
