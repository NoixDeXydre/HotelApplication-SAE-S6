package fr.adriencaubel.hotel.infraTest;

import fr.adriencaubel.hotel.domain.RoomTypePrice;
import fr.adriencaubel.hotel.infra.RoomTypeRateRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RoomTypeRateRepositoryTest {

    @Test
    @DisplayName("findByRoomTypeId returns stubbed list")
    void findByRoomTypeIdReturnsStubbedList() {
        // given
        RoomTypeRateRepository repository = mock(RoomTypeRateRepository.class);
        RoomTypePrice price = new RoomTypePrice();
        when(repository.findByRoomTypeId(1L)).thenReturn(List.of(price));

        // when
        List<RoomTypePrice> result = repository.findByRoomTypeId(1L);

        // then
        assertEquals(1, result.size());
    }
}
