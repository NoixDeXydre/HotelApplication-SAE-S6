package fr.adriencaubel.hotel.adapter.persistence.mapper;

import fr.adriencaubel.hotel.adapter.persistence.entity.InventoryEntity;
import fr.adriencaubel.hotel.domain.entity.Inventory;

public class InventoryPersistenceMapper {
    public Inventory toDomain(InventoryEntity inventoryEntity) {
        return new Inventory(inventoryEntity.getId(), inventoryEntity.getRoomType(),
                             inventoryEntity.getReservedRooms(), inventoryEntity.getTotalRooms(),
                             inventoryEntity.getDate());
    }

    public InventoryEntity toEntity(Inventory inventory) {
        return new InventoryEntity(inventory.getId(), inventory.getRoomType(),
                inventory.getReservedRooms(), inventory.getTotalRooms(),
                inventory.getDate());
    }
}
