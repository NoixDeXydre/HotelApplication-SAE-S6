package fr.adriencaubel.hotel.domain.ports.out;

import fr.adriencaubel.hotel.domain.RoomTypePrice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomTypeRateRepositoryPort {

    List<RoomTypePrice> findByRoomTypeId(Long roomTypeId);
}