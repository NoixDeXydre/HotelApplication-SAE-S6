package fr.adriencaubel.hotel.api;

import fr.adriencaubel.hotel.api.dto.BookingRequest;
import fr.adriencaubel.hotel.domain.Booking;
import fr.adriencaubel.hotel.service.HotelService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;


@RestController
@RequestMapping("/bookings")
public class BookingController {
    
    private final HotelService hotelService;
    
    public BookingController(HotelService hotelService) {
        this.hotelService = hotelService;
    }
    
    @PostMapping
    public Booking reserve(@RequestBody @Valid BookingRequest req) {
        return hotelService.reserveRoom(req);
    }

    @PutMapping("/{id}/amount")
    public void updateAmount(@PathVariable Long id, @RequestBody BigDecimal amount) {
        hotelService.updateBookingAmount(id, amount);
    }
}
