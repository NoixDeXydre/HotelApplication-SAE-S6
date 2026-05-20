package fr.adriencaubel.hotel.service;

import fr.adriencaubel.hotel.domain.Booking;
import fr.adriencaubel.hotel.domain.RoomType;
import fr.adriencaubel.hotel.infra.BookingRepository;
import fr.adriencaubel.hotel.infra.InventoryRepository;
import fr.adriencaubel.hotel.infra.RoomTypeRepository;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class HotelMetrics {

    private final BookingRepository bookingRepository;
    private final RoomTypeRepository roomTypeRepository;
    private final InventoryRepository inventoryRepository;
    private final MeterRegistry meterRegistry;

    // Variables en mémoire partagées avec Grafana
    private final AtomicLong metricTotalBookings = new AtomicLong(0);
    private final AtomicReference<Double> metricTotalRevenue = new AtomicReference<>(0.0);
    private final AtomicReference<Double> metricOccupancyRate = new AtomicReference<>(0.0);
    private final ConcurrentHashMap<String, AtomicLong> statusGauges = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, AtomicReference<Double>> revenueByRoomTypeGauges = new ConcurrentHashMap<>();

    public HotelMetrics(BookingRepository bookingRepository,
                               RoomTypeRepository roomTypeRepository,
                               InventoryRepository inventoryRepository,
                               MeterRegistry meterRegistry) {
        this.bookingRepository = bookingRepository;
        this.roomTypeRepository = roomTypeRepository;
        this.inventoryRepository = inventoryRepository;
        this.meterRegistry = meterRegistry;

        Gauge.builder("hotel.bookings.total", metricTotalBookings, AtomicLong::get).register(meterRegistry);
        Gauge.builder("hotel.revenue.total", metricTotalRevenue, AtomicReference<Double>::get).register(meterRegistry);
        Gauge.builder("hotel.occupancy.rate", metricOccupancyRate, AtomicReference<Double>::get).register(meterRegistry);
    }

    // 5 secondes
    @Scheduled(fixedRate = 5000)
    public void refreshMetrics() {
        System.out.println("Mise à jour automatique des métriques pour Grafana...");

        // 1. Calcul des réservations et revenus
        List<Booking> bookings = bookingRepository.findAll();
        metricTotalBookings.set(bookings.size());

        BigDecimal totalRevenue = bookings.stream()
                .filter(b -> "CONFIRMED".equals(b.getStatus()))
                .map(Booking::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        metricTotalRevenue.set(totalRevenue.doubleValue());

        // 2. Par statut
        Map<String, Long> bookingsByStatus = bookings.stream()
                .collect(Collectors.groupingBy(Booking::getStatus, Collectors.counting()));
        bookingsByStatus.forEach((status, count) -> {
            statusGauges.computeIfAbsent(status, s -> {
                AtomicLong al = new AtomicLong();
                Gauge.builder("hotel.bookings.by_status", al, AtomicLong::get).tag("status", s).register(meterRegistry);
                return al;
            }).set(count);
        });

        LocalDate today = LocalDate.now();
        int totalCapacity = 0;
        int reservedToday = 0;
        List<RoomType> roomTypes = roomTypeRepository.findAll();

        for (RoomType rt : roomTypes) {
            totalCapacity += rt.getTotalRooms();
            Integer reserved = inventoryRepository.findByRoomTypeAndDateBetween(rt.getId(), today, today).size();
            if (reserved != null) {
                reservedToday += reserved;
            }
        }

        double occupancyRate = totalCapacity == 0 ? 0 : ((double) reservedToday / totalCapacity) * 100;
        metricOccupancyRate.set(occupancyRate);
    }
}