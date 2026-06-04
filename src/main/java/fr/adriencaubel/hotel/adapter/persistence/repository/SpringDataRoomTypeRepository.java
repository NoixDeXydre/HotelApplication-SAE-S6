package fr.adriencaubel.hotel.adapter.persistence.repository;

import fr.adriencaubel.hotel.adapter.persistence.entity.RoomTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataRoomTypeRepository extends JpaRepository<RoomTypeEntity, Long> {
}
