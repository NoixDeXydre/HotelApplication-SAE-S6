package fr.adriencaubel.hotel.api;


import fr.adriencaubel.hotel.api.dto.DashboardResponse;
import fr.adriencaubel.hotel.service.DashboardMetrics;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    private final DashboardMetrics dashboardMetrics;

    public DashboardController(DashboardMetrics dashboardMetrics) {
        this.dashboardMetrics = dashboardMetrics;
    }

    @GetMapping
    public DashboardResponse getDashboard() {

        return new DashboardResponse(
                dashboardMetrics.getTotalBookings(),
                dashboardMetrics.getTotalRevenue(),
                dashboardMetrics.getOccupancyRate(),
                dashboardMetrics.getBookingsByStatus(),
                dashboardMetrics.getRevenueByRoomType()
        );
    }
}