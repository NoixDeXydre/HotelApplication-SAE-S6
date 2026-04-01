package fr.adriencaubel.hotel.infraTest;

import fr.adriencaubel.hotel.domain.RoomType;
import fr.adriencaubel.hotel.infra.RoomTypeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RoomTypeRepositoryTest {

    @Test
    @DisplayName("save can be stubbed on repository")
    void saveCanBeStubbedOnRepository() {
        // given
        RoomTypeRepository repository = mock(RoomTypeRepository.class);
        RoomType roomType = new RoomType();
        when(repository.save(roomType)).thenReturn(roomType);

        // when
        RoomType result = repository.save(roomType);

        // then
        assertSame(roomType, result);
    }
}
