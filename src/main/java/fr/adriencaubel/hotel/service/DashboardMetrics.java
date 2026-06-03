package fr.adriencaubel.hotel.service;

import fr.adriencaubel.hotel.api.RoomTypeController;
import fr.adriencaubel.hotel.domain.Booking;
import fr.adriencaubel.hotel.domain.BookingOption;
import fr.adriencaubel.hotel.domain.RoomType;
import fr.adriencaubel.hotel.infra.BookingRepository;
import fr.adriencaubel.hotel.infra.InventoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DashboardMetrics {

    private final BookingRepository bookingRepository;
    private final InventoryRepository inventoryRepository;
    private final RoomTypeController roomTypeRepository;

    public DashboardMetrics(BookingRepository bookingRepository, InventoryRepository inventoryRepository,
                            RoomTypeController roomTypeRepository) {
        this.bookingRepository = bookingRepository;
        this.inventoryRepository = inventoryRepository;
        this.roomTypeRepository = roomTypeRepository;
    }

    public long getTotalBookings() {
        return bookingRepository.findAll().size();
    }

    public BigDecimal getTotalRevenue() {

        List<Booking> bookings = bookingRepository.findAll();
        return bookings.stream()
                .filter(b -> "CONFIRMED".equals(b.getStatus()))
                .map(Booking::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Transactional(readOnly = true)
    public Map<String, BigDecimal> getRevenueByRoomType() {

        var bookings = bookingRepository.findAll();
        var revenueByRoomType = new HashMap<String, BigDecimal>();
        for (Booking b : bookings) {

            List<BookingOption> options = b.getOptions();
            options.forEach(o -> System.out.println(o.getType()));
            RoomType roomType = roomTypeRepository
                    .findById(b.getRoomType().getId());

            if (roomType == null) continue;

            revenueByRoomType.merge(
                    roomType.getName(),
                    b.getAmount(),
                    BigDecimal::add
            );
        }

        return revenueByRoomType;
    }

    public Map<String, Long> getBookingsByStatus() {

        return bookingRepository.findAll().stream()
                .collect(Collectors.groupingBy(
                        Booking::getStatus,
                        Collectors.counting()
                ));
    }

    public double getOccupancyRate() {

        LocalDate today = LocalDate.now();

        int totalCapacity = 0;
        int reservedToday = 0;

        List<RoomType> roomTypes = roomTypeRepository.findAll();

        for (RoomType rt : roomTypes) {

            totalCapacity += rt.getTotalRooms();

            Integer reserved = inventoryRepository
                    .findByRoomTypeAndDateBetween(rt.getId(), today, today).size();

            if (reserved != null) {
                reservedToday += reserved;
            }
        }

        return totalCapacity == 0 ? 0 :
                ((double) reservedToday / totalCapacity) * 100;
    }
}