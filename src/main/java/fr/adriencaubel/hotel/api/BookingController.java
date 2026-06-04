package fr.adriencaubel.hotel.api;

import fr.adriencaubel.hotel.api.dto.BookingRequest;
import fr.adriencaubel.hotel.domain.entity.Booking;
import fr.adriencaubel.hotel.service.HotelService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;


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
}
