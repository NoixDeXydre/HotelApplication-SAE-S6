package fr.adriencaubel.hotel.domain.ports.out.repository;

import fr.adriencaubel.hotel.domain.entity.RoomType;

import java.util.Optional;

public interface RoomTypeRepositoryPort {
    Optional<RoomType> findById(Long roomTypeId);
}
