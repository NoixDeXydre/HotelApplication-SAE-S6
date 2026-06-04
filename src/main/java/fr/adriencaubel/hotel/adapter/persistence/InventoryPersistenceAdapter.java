package fr.adriencaubel.hotel.adapter.persistence;

import fr.adriencaubel.hotel.adapter.persistence.mapper.InventoryPersistenceMapper;
import fr.adriencaubel.hotel.adapter.persistence.repository.SpringDataInventoryRepository;
import fr.adriencaubel.hotel.domain.entity.Booking;
import fr.adriencaubel.hotel.domain.entity.Inventory;

public class InventoryPersistenceAdapter {
    private final InventoryPersistenceMapper inventoryPersistenceMapper;
    private final SpringDataInventoryRepository springDataInventoryRepository;

    public InventoryPersistenceAdapter(InventoryPersistenceMapper inventoryPersistenceMapper,
                                       SpringDataInventoryRepository springDataInventoryRepository) {
        this.inventoryPersistenceMapper = inventoryPersistenceMapper;
        this.springDataInventoryRepository = springDataInventoryRepository;
    }

    public Inventory save(Inventory inventory) {
        return inventoryPersistenceMapper.toDomain(
                springDataInventoryRepository.save(inventoryPersistenceMapper.toEntity(inventory))
        );
    }
}
