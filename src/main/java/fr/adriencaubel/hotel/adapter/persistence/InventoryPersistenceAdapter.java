package fr.adriencaubel.hotel.adapter.persistence;

import fr.adriencaubel.hotel.adapter.persistence.mapper.InventoryPersistenceMapper;
import fr.adriencaubel.hotel.adapter.persistence.repository.SpringDataInventoryRepository;
import fr.adriencaubel.hotel.domain.entity.Booking;
import fr.adriencaubel.hotel.domain.entity.Inventory;
import fr.adriencaubel.hotel.domain.ports.out.repository.InventoryRepositoryPort;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class InventoryPersistenceAdapter implements InventoryRepositoryPort {
    private final InventoryPersistenceMapper inventoryPersistenceMapper;
    private final SpringDataInventoryRepository springDataInventoryRepository;

    public InventoryPersistenceAdapter(InventoryPersistenceMapper inventoryPersistenceMapper,
                                       SpringDataInventoryRepository springDataInventoryRepository) {
        this.inventoryPersistenceMapper = inventoryPersistenceMapper;
        this.springDataInventoryRepository = springDataInventoryRepository;
    }

    public void save(Inventory inventory) {
        inventoryPersistenceMapper.toDomain(
                springDataInventoryRepository.save(inventoryPersistenceMapper.toEntity(inventory))
        );
    }

    @Override
    public List<Inventory> findByRoomTypeAndDateBetween(Long roomTypeId, LocalDate from, LocalDate to) {
        return List.of();
    }

    @Override
    public boolean existsByRoomTypeIdAndDate(Long roomTypeId, LocalDate date) {
        return false;
    }

    @Override
    public Optional<Inventory> findByRoomTypeIdAndDate(Long roomTypeId, LocalDate date) {
        return Optional.empty();
    }
}
