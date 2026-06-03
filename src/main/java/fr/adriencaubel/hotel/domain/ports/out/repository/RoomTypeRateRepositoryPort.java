package fr.adriencaubel.hotel.domain.ports.out.repository;

import fr.adriencaubel.hotel.domain.entity.RoomTypePrice;

import java.util.List;

public interface RoomTypeRateRepositoryPort {

    List<RoomTypePrice> findByRoomTypeId(Long roomTypeId);
}