package fr.adriencaubel.hotel.domainTest;

import fr.adriencaubel.hotel.domain.Inventory;
import fr.adriencaubel.hotel.domain.RoomType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InventoryTest {

    @Test
    @DisplayName("availableRooms returns total minus reserved")
    void availableRoomsReturnsTotalMinusReserved() {
        // given
        Inventory inventory = new Inventory(new RoomType(), LocalDate.of(2024, 1, 1), 10);
        inventory.setReservedRooms(3);

        // when
        int available = inventory.availableRooms();

        // then
        assertEquals(7, available);
    }

    @Test
    @DisplayName("canReserve uses available rooms")
    void canReserveUsesAvailableRooms() {
        // given
        Inventory inventory = new Inventory(new RoomType(), LocalDate.of(2024, 1, 1), 5);
        inventory.setReservedRooms(4);

        // when
        boolean canReserve1 = inventory.canReserve(1);
        boolean canReserve2 = inventory.canReserve(2);

        // then
        assertEquals(true, canReserve1);
        assertEquals(false, canReserve2);
    }

    @Test
    @DisplayName("reserve increases reserved rooms when possible")
    void reserveIncreasesReservedRoomsWhenPossible() {
        // given
        Inventory inventory = new Inventory(new RoomType(), LocalDate.of(2024, 1, 1), 5);
        inventory.setReservedRooms(1);

        // when
        inventory.reserve(2);

        // then
        assertEquals(3, inventory.getReservedRooms());
    }

    @Test
    @DisplayName("reserve throws when insufficient rooms")
    void reserveThrowsWhenInsufficientRooms() {
        // given
        Inventory inventory = new Inventory(new RoomType(), LocalDate.of(2024, 1, 1), 2);
        inventory.setReservedRooms(2);

        // when
        IllegalStateException ex = assertThrows(IllegalStateException.class, () -> inventory.reserve(1));

        // then
        assertEquals("Not enough rooms available for 2024-01-01", ex.getMessage());
    }

    @Test
    @DisplayName("release decreases reserved rooms")
    void releaseDecreasesReservedRooms() {
        // given
        Inventory inventory = new Inventory(new RoomType(), LocalDate.of(2024, 1, 1), 5);
        inventory.setReservedRooms(3);

        // when
        inventory.release(2);

        // then
        assertEquals(1, inventory.getReservedRooms());
    }

    @Test
    @DisplayName("release throws when releasing too many")
    void releaseThrowsWhenReleasingTooMany() {
        // given
        Inventory inventory = new Inventory(new RoomType(), LocalDate.of(2024, 1, 1), 5);
        inventory.setReservedRooms(1);

        // when
        IllegalStateException ex = assertThrows(IllegalStateException.class, () -> inventory.release(2));

        // then
        assertEquals("Cannot release more than reserved", ex.getMessage());
    }

    @Test
    @DisplayName("getDate returns assigned date")
    void getDateReturnsAssignedDate() {
        // given
        Inventory inventory = new Inventory(new RoomType(), LocalDate.of(2024, 1, 1), 1);

        // when
        LocalDate date = inventory.getDate();

        // then
        assertEquals(LocalDate.of(2024, 1, 1), date);
    }

    @Test
    @DisplayName("constructor throws when roomType is null")
    void constructorThrowsWhenRoomTypeIsNull() {
        // when / then
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> new Inventory(null, LocalDate.of(2024, 1, 1), 5)
        );
        assertEquals("RoomType is not existing", ex.getMessage());
    }

    @Test
    @DisplayName("constructor initialises reservedRooms to zero")
    void constructorInitialisesReservedRoomsToZero() {
        // when
        Inventory inventory = new Inventory(new RoomType(), LocalDate.of(2024, 1, 1), 10);

        // then
        assertEquals(0, inventory.getReservedRooms());
        assertEquals(10, inventory.getTotalRooms());
    }

    @Test
    @DisplayName("addReservedRooms increments reserved count without capacity check")
    void addReservedRoomsIncrementsReservedCountWithoutCapacityCheck() {
        // given
        Inventory inventory = new Inventory(new RoomType(), LocalDate.of(2024, 1, 1), 5);
        inventory.setReservedRooms(2);

        // when
        inventory.addReservedRooms(3);

        // then
        assertEquals(5, inventory.getReservedRooms());
    }
}
