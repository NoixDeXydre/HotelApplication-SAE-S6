package fr.adriencaubel.hotel.domainTest;

import fr.adriencaubel.hotel.domain.RoomType;
import fr.adriencaubel.hotel.domain.RoomTypePrice;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RoomTypeTest {

    @Test
    @DisplayName("getters and setters update fields")
    void gettersAndSettersUpdateFields() {
        // given
        RoomType roomType = new RoomType();

        // when
        roomType.setId(1L);
        roomType.setName("Suite");
        roomType.setTotalRooms(4);

        // then
        assertEquals(1L, roomType.getId());
        assertEquals("Suite", roomType.getName());
        assertEquals(4, roomType.getTotalRooms());
    }

    @Test
    @DisplayName("prices list can be set")
    void pricesListCanBeSet() {
        // given
        RoomType roomType = new RoomType();
        RoomTypePrice price = new RoomTypePrice();

        // when
        roomType.setPrices(List.of(price));

        // then
        assertEquals(1, roomType.getPrices().size());
    }
}
