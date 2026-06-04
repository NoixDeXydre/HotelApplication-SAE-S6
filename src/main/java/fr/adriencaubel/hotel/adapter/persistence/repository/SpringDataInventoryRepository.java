package fr.adriencaubel.hotel.adapter.persistence.repository;

import fr.adriencaubel.hotel.adapter.persistence.entity.BookingEntity;
import fr.adriencaubel.hotel.adapter.persistence.entity.InventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataInventoryRepository extends JpaRepository<InventoryEntity, Long> {
}
