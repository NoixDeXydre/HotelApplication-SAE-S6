package fr.adriencaubel.hotel.domainTest;

import fr.adriencaubel.hotel.domain.RoomType;
import fr.adriencaubel.hotel.domain.RoomTypePrice;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class RoomTypePriceTest {

    @Test
    @DisplayName("setters and getters work")
    void settersAndGettersWork() {
        // given
        RoomTypePrice price = new RoomTypePrice();
        RoomType roomType = new RoomType();

        // when
        price.setStartDate(LocalDate.of(2024, 1, 1));
        price.setEndDate(LocalDate.of(2024, 1, 10));
        price.setPricePerNight(new BigDecimal("99.99"));
        price.setRoomType(roomType);

        // then
        assertEquals(LocalDate.of(2024, 1, 1), price.getStartDate());
        assertEquals(LocalDate.of(2024, 1, 10), price.getEndDate());
        assertEquals(new BigDecimal("99.99"), price.getPricePerNight());
        assertSame(roomType, price.getRoomType());
    }
}
