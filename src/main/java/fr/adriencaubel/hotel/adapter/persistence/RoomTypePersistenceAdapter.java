package fr.adriencaubel.hotel.adapter.persistence;

import fr.adriencaubel.hotel.adapter.persistence.mapper.RoomTypePersistenceMapper;
import fr.adriencaubel.hotel.adapter.persistence.repository.SpringDataRoomTypeRepository;
import fr.adriencaubel.hotel.domain.entity.RoomType;

public class RoomTypePersistenceAdapter {
    private final RoomTypePersistenceMapper roomTypePersistenceMapper;
    private final SpringDataRoomTypeRepository springDataRoomTypeRepository;

    public RoomTypePersistenceAdapter(RoomTypePersistenceMapper inventoryPersistenceMapper,
                                       SpringDataRoomTypeRepository springDataInventoryRepository) {
        this.roomTypePersistenceMapper = inventoryPersistenceMapper;
        this.springDataRoomTypeRepository = springDataInventoryRepository;
    }

    public RoomType save(RoomType roomType) {
        return roomTypePersistenceMapper.toDomain(
                springDataRoomTypeRepository.save(roomTypePersistenceMapper.toEntity(roomType))
        );
    }
}
