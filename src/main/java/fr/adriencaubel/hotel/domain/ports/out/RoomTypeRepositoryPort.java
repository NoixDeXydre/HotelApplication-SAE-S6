package fr.adriencaubel.hotel.domain.ports.out;

import fr.adriencaubel.hotel.domain.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.ScopedValue;
import java.util.Optional;

public interface RoomTypeRepositoryPort {
    Optional<RoomType> findById(Long roomTypeId);
}
