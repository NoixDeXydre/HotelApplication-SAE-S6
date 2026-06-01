package fr.adriencaubel.hotel.service;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class HotelMetrics {

    // Raffraichissement en millisecondes
    private final int TEMPS_RAFFRAICHISSEMENT_METRIQUES = 5000;

    private final DashboardMetrics dashboardMetrics;
    private final MeterRegistry meterRegistry;

    // Variables partagées avec Grafana
    private final AtomicLong metricTotalBookings = new AtomicLong(0);
    private final AtomicReference<Double> metricTotalRevenue = new AtomicReference<>(0.0);
    private final AtomicReference<Double> metricOccupancyRate = new AtomicReference<>(0.0);

    public HotelMetrics(DashboardMetrics dashboardMetrics,
                        MeterRegistry meterRegistry) {

        this.dashboardMetrics = dashboardMetrics;
        this.meterRegistry = meterRegistry;

        Gauge.builder("hotel.bookings.total", metricTotalBookings, AtomicLong::get).register(meterRegistry);
        Gauge.builder("hotel.revenue.total", metricTotalRevenue, AtomicReference<Double>::get).register(meterRegistry);
        Gauge.builder("hotel.occupancy.rate", metricOccupancyRate, AtomicReference<Double>::get).register(meterRegistry);
    }

    @Scheduled(fixedRate = TEMPS_RAFFRAICHISSEMENT_METRIQUES)
    public void refreshMetrics() {
        metricTotalRevenue.set(dashboardMetrics.getTotalRevenue().doubleValue());
        metricOccupancyRate.set(dashboardMetrics.getOccupancyRate());
        metricTotalBookings.set(dashboardMetrics.getTotalBookings());
    }
}