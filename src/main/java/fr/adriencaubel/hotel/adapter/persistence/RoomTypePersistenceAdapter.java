package fr.adriencaubel.hotel.adapter.persistence;

import fr.adriencaubel.hotel.adapter.persistence.mapper.RoomTypePersistenceMapper;
import fr.adriencaubel.hotel.adapter.persistence.repository.SpringDataRoomTypeRepository;
import fr.adriencaubel.hotel.domain.entity.RoomType;
import fr.adriencaubel.hotel.domain.ports.out.repository.RoomTypeRepositoryPort;

import java.util.Optional;

public class RoomTypePersistenceAdapter implements RoomTypeRepositoryPort {
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

    @Override
    public Optional<RoomType> findById(Long roomTypeId) {
        return Optional.empty();
    }
}
