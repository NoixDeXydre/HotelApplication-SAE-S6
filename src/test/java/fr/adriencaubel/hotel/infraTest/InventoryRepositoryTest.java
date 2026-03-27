package fr.adriencaubel.hotel.infraTest;

import fr.adriencaubel.hotel.domain.Inventory;
import fr.adriencaubel.hotel.infra.InventoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class InventoryRepositoryTest {

    @Test
    @DisplayName("findByRoomTypeAndDateBetween returns stubbed list")
    void findByRoomTypeAndDateBetweenReturnsStubbedList() {
        // given
        InventoryRepository repository = mock(InventoryRepository.class);
        Inventory inventory = new Inventory();
        when(repository.findByRoomTypeAndDateBetween(1L, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 2)))
                .thenReturn(List.of(inventory));

        // when
        List<Inventory> result = repository.findByRoomTypeAndDateBetween(
                1L, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 2)
        );

        // then
        assertEquals(1, result.size());
        assertSame(inventory, result.get(0));
    }

    @Test
    @DisplayName("existsByRoomTypeIdAndDate returns stubbed value")
    void existsByRoomTypeIdAndDateReturnsStubbedValue() {
        // given
        InventoryRepository repository = mock(InventoryRepository.class);
        when(repository.existsByRoomTypeIdAndDate(1L, LocalDate.of(2024, 1, 1))).thenReturn(true);

        // when
        boolean exists = repository.existsByRoomTypeIdAndDate(1L, LocalDate.of(2024, 1, 1));

        // then
        assertEquals(true, exists);
    }

    @Test
    @DisplayName("findByRoomTypeIdAndDate returns stubbed optional")
    void findByRoomTypeIdAndDateReturnsStubbedOptional() {
        // given
        InventoryRepository repository = mock(InventoryRepository.class);
        Inventory inventory = new Inventory();
        when(repository.findByRoomTypeIdAndDate(1L, LocalDate.of(2024, 1, 1)))
                .thenReturn(Optional.of(inventory));

        // when
        Optional<Inventory> result = repository.findByRoomTypeIdAndDate(1L, LocalDate.of(2024, 1, 1));

        // then
        assertSame(inventory, result.orElseThrow());
    }
}
