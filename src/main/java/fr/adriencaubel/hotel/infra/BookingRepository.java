package fr.adriencaubel.hotel.infra;

import fr.adriencaubel.hotel.domain.Booking;
import fr.adriencaubel.hotel.domain.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.ScopedValue;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    Optional<Booking> findByIdForUpdate(Long bookingId);
}
