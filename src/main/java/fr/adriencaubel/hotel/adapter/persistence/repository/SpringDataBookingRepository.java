package fr.adriencaubel.hotel.adapter.persistence.repository;

import fr.adriencaubel.hotel.adapter.persistence.entity.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataBookingRepository extends JpaRepository<BookingEntity, Long> {
}
