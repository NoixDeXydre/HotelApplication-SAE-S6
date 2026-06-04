package fr.adriencaubel.hotel.domain.ports.out.repository;

import fr.adriencaubel.hotel.domain.entity.Inventory;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface InventoryRepositoryPort {

    List<Inventory> findByRoomTypeAndDateBetween(Long roomTypeId, LocalDate from, LocalDate to);

    boolean existsByRoomTypeIdAndDate(Long roomTypeId, LocalDate date);

    Optional<Inventory> findByRoomTypeIdAndDate(Long roomTypeId, LocalDate date);

    void save(Inventory inventory);
}
