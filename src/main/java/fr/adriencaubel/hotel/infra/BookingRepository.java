package fr.adriencaubel.hotel.infra;

import fr.adriencaubel.hotel.domain.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}
