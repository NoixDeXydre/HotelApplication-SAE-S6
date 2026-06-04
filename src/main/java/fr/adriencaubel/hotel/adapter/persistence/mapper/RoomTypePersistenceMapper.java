package fr.adriencaubel.hotel.adapter.persistence.mapper;

import fr.adriencaubel.hotel.adapter.persistence.entity.InventoryEntity;
import fr.adriencaubel.hotel.adapter.persistence.entity.RoomTypeEntity;
import fr.adriencaubel.hotel.domain.entity.Inventory;
import fr.adriencaubel.hotel.domain.entity.RoomType;

public class RoomTypePersistenceMapper {
    public RoomType toDomain(RoomTypeEntity inventoryEntity) {
        return new RoomType(inventoryEntity.getId(), inventoryEntity.getPrices(),
                inventoryEntity.getName(), inventoryEntity.getTotalRooms());
    }

    public RoomTypeEntity toEntity(RoomType inventory) {
        return new RoomTypeEntity(inventory.getId(), inventory.getPrices(),
                inventory.getName(), inventory.getTotalRooms());
    }
}
