package fr.adriencaubel.hotel.adapter.persistence.repository;

import fr.adriencaubel.hotel.adapter.persistence.entity.BookingOptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataBookingOptionRepository extends JpaRepository<BookingOptionEntity, Long> {
}
