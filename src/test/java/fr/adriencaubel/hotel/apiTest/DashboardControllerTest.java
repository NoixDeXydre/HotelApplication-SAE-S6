package fr.adriencaubel.hotel.apiTest;

import fr.adriencaubel.hotel.api.DashboardController;
import fr.adriencaubel.hotel.api.dto.DashboardResponse;
import fr.adriencaubel.hotel.domain.Booking;
import fr.adriencaubel.hotel.domain.Inventory;
import fr.adriencaubel.hotel.domain.RoomType;
import fr.adriencaubel.hotel.infra.BookingRepository;
import fr.adriencaubel.hotel.infra.InventoryRepository;
import fr.adriencaubel.hotel.infra.RoomTypeRepository;
import fr.adriencaubel.hotel.service.DashboardMetrics;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DashboardControllerTest {

    @Test
    @DisplayName("getDashboard aggregates totals and occupancy")
    void getDashboardAggregatesTotalsAndOccupancy() {

        // given
        DashboardMetrics dashboardMetrics = mock(DashboardMetrics.class);
        DashboardController controller = new DashboardController(dashboardMetrics);

        Booking booking1 = new Booking(
                "customer1@example.com",
                "Doe",
                "John",
                new RoomType(),
                LocalDate.of(2026, 1, 1),
                LocalDate.of(2026, 1, 2),
                1,
                new BigDecimal("100.00"),
                "CONFIRMED"
        );
        booking1.setId(1L);
        booking1.setStatus("CONFIRMED");
        booking1.setAmount(new BigDecimal("100.00"));

        Booking booking2 = new Booking(
                "customer2@example.com",
                "Smith",
                "Jane",
                new RoomType(),
                LocalDate.of(2026, 1, 1),
                LocalDate.of(2026, 1, 2),
                1,
                new BigDecimal("50.00"),
                "PENDING"
        );
        booking2.setId(2L);
        booking2.setStatus("PENDING");
        booking2.setAmount(new BigDecimal("50.00"));

        RoomType rt1 = new RoomType();
        rt1.setId(1L);
        rt1.setName("Standard");
        rt1.setTotalRooms(10);

        RoomType rt2 = new RoomType();
        rt2.setId(2L);
        rt2.setName("Deluxe");
        rt2.setTotalRooms(20);

        when(dashboardMetrics.getTotalBookings()).thenReturn(2l);
        when(dashboardMetrics.getTotalRevenue()).thenReturn(new BigDecimal("100.00"));
        when(dashboardMetrics.getOccupancyRate()).thenReturn(23.333333333333332d);

        Map<String, BigDecimal> rooms = new HashMap<>();
        rooms.put("Standard",  new BigDecimal("100.00"));
        rooms.put("Deluxe",  new BigDecimal("50.00"));
        when(dashboardMetrics.getRevenueByRoomType()).thenReturn(rooms);

        Map<String, Long> status = new HashMap<>();
        status.put("CONFIRMED",  1l);
        status.put("PENDING",  1l);
        when(dashboardMetrics.getBookingsByStatus()).thenReturn(status);

        // when
        DashboardResponse response = controller.getDashboard();

        // then
        assertEquals(2, response.totalBookings);
        assertEquals(new BigDecimal("100.00"), response.totalRevenue);
        assertEquals(1L, response.bookingsByStatus.get("CONFIRMED"));
        assertEquals(1L, response.bookingsByStatus.get("PENDING"));
        assertEquals(new BigDecimal("100.00"), response.revenueByRoomType.get("Standard"));
        assertEquals(new BigDecimal("50.00"), response.revenueByRoomType.get("Deluxe"));
        assertEquals(23.333333333333332d, response.occupancyRate, 0.0000000001d);
    }
}
