package fr.adriencaubel.hotel.apiTest.dtoTest;

import fr.adriencaubel.hotel.api.dto.DashboardResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DashboardResponseTest {

    @Test
    @DisplayName("constructor sets all fields")
    void constructorSetsAllFields() {
        // given
        Map<String, Long> bookingsByStatus = Map.of("CONFIRMED", 2L);
        Map<String, BigDecimal> revenueByRoomType = Map.of("Standard", new BigDecimal("120.00"));

        // when
        DashboardResponse response = new DashboardResponse(
                2L,
                new BigDecimal("120.00"),
                80.0d,
                bookingsByStatus,
                revenueByRoomType
        );

        // then
        assertEquals(2L, response.totalBookings);
        assertEquals(new BigDecimal("120.00"), response.totalRevenue);
        assertEquals(80.0d, response.occupancyRate);
        assertEquals(bookingsByStatus, response.bookingsByStatus);
        assertEquals(revenueByRoomType, response.revenueByRoomType);
    }
}
