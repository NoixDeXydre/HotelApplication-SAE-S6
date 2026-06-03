package fr.adriencaubel.hotel.infra;

import fr.adriencaubel.hotel.domain.entity.RoomTypePrice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomTypeRateRepository
        extends JpaRepository<RoomTypePrice, Long> {

    List<RoomTypePrice> findByRoomTypeId(Long roomTypeId);
}