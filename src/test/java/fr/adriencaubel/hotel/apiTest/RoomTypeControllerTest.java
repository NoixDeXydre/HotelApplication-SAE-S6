package fr.adriencaubel.hotel.apiTest;

import fr.adriencaubel.hotel.api.RoomTypeController;
import fr.adriencaubel.hotel.domain.RoomType;
import fr.adriencaubel.hotel.infra.RoomTypeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RoomTypeControllerTest {

    @Test
    @DisplayName("findAll returns repository results")
    void findAllReturnsRepositoryResults() {
        // given
        RoomTypeRepository repository = mock(RoomTypeRepository.class);
        RoomTypeController controller = new RoomTypeController(repository);
        RoomType roomType = new RoomType();
        when(repository.findAll()).thenReturn(List.of(roomType));

        // when
        List<RoomType> result = controller.findAll();

        // then
        assertEquals(1, result.size());
        assertSame(roomType, result.get(0));
    }

    @Test
    @DisplayName("findById returns value when present")
    void findByIdReturnsValueWhenPresent() {
        // given
        RoomTypeRepository repository = mock(RoomTypeRepository.class);
        RoomTypeController controller = new RoomTypeController(repository);
        RoomType roomType = new RoomType();
        when(repository.findById(1L)).thenReturn(Optional.of(roomType));

        // when
        RoomType result = controller.findById(1L);

        // then
        assertSame(roomType, result);
    }

    @Test
    @DisplayName("findById throws when missing")
    void findByIdThrowsWhenMissing() {
        // given
        RoomTypeRepository repository = mock(RoomTypeRepository.class);
        RoomTypeController controller = new RoomTypeController(repository);
        when(repository.findById(1L)).thenReturn(Optional.empty());

        // when
        RuntimeException ex = assertThrows(RuntimeException.class, () -> controller.findById(1L));

        // then
        assertEquals("Not found", ex.getMessage());
    }

    @Test
    @DisplayName("create saves and returns room type")
    void createSavesAndReturnsRoomType() {
        // given
        RoomTypeRepository repository = mock(RoomTypeRepository.class);
        RoomTypeController controller = new RoomTypeController(repository);
        RoomType input = new RoomType();
        when(repository.save(input)).thenReturn(input);

        // when
        RoomType result = controller.create(input);

        // then
        assertSame(input, result);
    }

    @Test
    @DisplayName("update changes name and total rooms")
    void updateChangesNameAndTotalRooms() {
        // given
        RoomTypeRepository repository = mock(RoomTypeRepository.class);
        RoomTypeController controller = new RoomTypeController(repository);
        RoomType existing = new RoomType();
        existing.setName("Old");
        existing.setTotalRooms(1);
        RoomType updated = new RoomType();
        updated.setName("New");
        updated.setTotalRooms(5);
        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        when(repository.save(existing)).thenReturn(existing);

        // when
        RoomType result = controller.update(1L, updated);

        // then
        assertSame(existing, result);
        assertEquals("New", existing.getName());
        assertEquals(5, existing.getTotalRooms());
    }

    @Test
    @DisplayName("delete removes room type by id")
    void deleteRemovesRoomTypeById() {
        // given
        RoomTypeRepository repository = mock(RoomTypeRepository.class);
        RoomTypeController controller = new RoomTypeController(repository);

        // when
        controller.delete(1L);

        // then
        verify(repository).deleteById(1L);
    }
}
