package fr.adriencaubel.hotel.infra;

import fr.adriencaubel.hotel.domain.entity.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomTypeRepository extends JpaRepository<RoomType, Long> {
}
