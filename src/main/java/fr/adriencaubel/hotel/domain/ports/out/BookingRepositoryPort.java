package fr.adriencaubel.hotel.domain.ports.out;

import fr.adriencaubel.hotel.domain.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepositoryPort {
    void save(Booking booking);
}
