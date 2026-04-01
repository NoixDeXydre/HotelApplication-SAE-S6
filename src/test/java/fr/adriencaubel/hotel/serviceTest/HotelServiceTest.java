package fr.adriencaubel.hotel.serviceTest;

import fr.adriencaubel.hotel.api.dto.AvailabilityResponse;
import fr.adriencaubel.hotel.api.dto.BookingRequest;
import fr.adriencaubel.hotel.domain.Booking;
import fr.adriencaubel.hotel.domain.BookingOption;
import fr.adriencaubel.hotel.domain.Inventory;
import fr.adriencaubel.hotel.domain.RoomType;
import fr.adriencaubel.hotel.infra.BookingRepository;
import fr.adriencaubel.hotel.infra.InventoryRepository;
import fr.adriencaubel.hotel.infra.RoomTypeRepository;
import fr.adriencaubel.hotel.service.EmailSender;
import fr.adriencaubel.hotel.service.HotelService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class HotelServiceTest {

    @Test
    @DisplayName("checkAvailability returns capacity when no inventory")
    void checkAvailabilityReturnsCapacityWhenNoInventory() {
        // given
        RoomTypeRepository roomTypeRepo = mock(RoomTypeRepository.class);
        BookingRepository bookingRepo = mock(BookingRepository.class);
        InventoryRepository inventoryRepo = mock(InventoryRepository.class);
        EmailSender emailSender = mock(EmailSender.class);
        HotelService service = new HotelService(roomTypeRepo, bookingRepo, inventoryRepo, emailSender);

        RoomType roomType = new RoomType();
        roomType.setTotalRooms(5);
        when(roomTypeRepo.findById(1L)).thenReturn(Optional.of(roomType));
        when(inventoryRepo.findByRoomTypeAndDateBetween(1L, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 2)))
                .thenReturn(List.of());

        // when
        AvailabilityResponse response = service.checkAvailability(
                1L,
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 1, 2),
                2
        );

        // then
        assertEquals(true, response.available);
        assertEquals(5, response.remainingMin);
    }

    @Test
    @DisplayName("checkAvailability uses minimum remaining")
    void checkAvailabilityUsesMinimumRemaining() {
        // given
        RoomTypeRepository roomTypeRepo = mock(RoomTypeRepository.class);
        BookingRepository bookingRepo = mock(BookingRepository.class);
        InventoryRepository inventoryRepo = mock(InventoryRepository.class);
        EmailSender emailSender = mock(EmailSender.class);
        HotelService service = new HotelService(roomTypeRepo, bookingRepo, inventoryRepo, emailSender);

        RoomType roomType = new RoomType();
        roomType.setTotalRooms(5);
        when(roomTypeRepo.findById(1L)).thenReturn(Optional.of(roomType));

        Inventory inv1 = new Inventory(new RoomType(), LocalDate.of(2024, 1, 1), 5);
        inv1.setReservedRooms(1);
        Inventory inv2 = new Inventory(new RoomType(), LocalDate.of(2024, 1, 2), 5);
        inv2.setReservedRooms(4);

        when(inventoryRepo.findByRoomTypeAndDateBetween(1L, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 3)))
                .thenReturn(List.of(inv1, inv2));

        // when
        AvailabilityResponse response = service.checkAvailability(
                1L,
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 1, 3),
                2
        );

        // then
        assertEquals(false, response.available);
        assertEquals(1, response.remainingMin);
    }

    @Test
    @DisplayName("reserveRoom creates booking with customer and options")
    void reserveRoomCreatesBookingWithCustomerAndOptions() {
        // given
        RoomTypeRepository roomTypeRepo = mock(RoomTypeRepository.class);
        BookingRepository bookingRepo = mock(BookingRepository.class);
        InventoryRepository inventoryRepo = mock(InventoryRepository.class);
        EmailSender emailSender = mock(EmailSender.class);
        HotelService service = spy(new HotelService(roomTypeRepo, bookingRepo, inventoryRepo, emailSender));

        RoomType roomType = new RoomType();
        roomType.setTotalRooms(5);
        when(roomTypeRepo.findById(1L)).thenReturn(Optional.of(roomType));

        BookingRequest request = new BookingRequest();
        request.roomTypeId = 1L;
        request.from = LocalDate.of(2024, 1, 1);
        request.to = LocalDate.of(2024, 1, 3);
        request.nomPrenomEmail = "Jean Dupont jean.dupont@example.com";
        request.amount = new BigDecimal("120.00");
        request.quantity = 2;
        request.options = List.of("ANNIVERSAIRE,Gateau", "FLEUR");

        doReturn(new AvailabilityResponse(1L, request.from, request.to, true, 5))
                .when(service).checkAvailability(1L, request.from, request.to, 2);
        doNothing().when(service).createIfMissing(any(), any(), anyInt());
        doNothing().when(service).addReserved(any(), any(), anyInt());

        when(bookingRepo.save(any(Booking.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // when
        Booking booking = service.reserveRoom(request);

        // then
        assertEquals("CONFIRMED", booking.getStatus());
        assertEquals(new BigDecimal("120.00"), booking.getAmount());
        assertEquals("Jean", booking.getNom());
        assertEquals("Dupont", booking.getPrenom());
        assertEquals("jean.dupont@example.com", booking.getEmail());
        assertEquals(2, booking.getOptions().size());

        BookingOption option1 = booking.getOptions().get(0);
        BookingOption option2 = booking.getOptions().get(1);
        assertEquals("ANNIVERSAIRE", option1.getType());
        assertEquals("Gateau", option1.getComment());
        assertEquals("FLEUR", option2.getType());
        assertEquals(null, option2.getComment());

        verify(emailSender).sendConfirmation(
                org.mockito.ArgumentMatchers.eq("customer@example.com"),
                org.mockito.ArgumentMatchers.anyString()
        );
    }

    @Test
    @DisplayName("reserveRoom rejects invalid date range")
    void reserveRoomRejectsInvalidDateRange() {
        // given
        RoomTypeRepository roomTypeRepo = mock(RoomTypeRepository.class);
        BookingRepository bookingRepo = mock(BookingRepository.class);
        InventoryRepository inventoryRepo = mock(InventoryRepository.class);
        EmailSender emailSender = mock(EmailSender.class);
        HotelService service = new HotelService(roomTypeRepo, bookingRepo, inventoryRepo, emailSender);

        BookingRequest request = new BookingRequest();
        request.from = LocalDate.of(2024, 1, 3);
        request.to = LocalDate.of(2024, 1, 1);

        // when
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> service.reserveRoom(request));

        // then
        assertEquals("to must be after from", ex.getMessage());
    }

    @Test
    @DisplayName("reserveRoom rejects when not available")
    void reserveRoomRejectsWhenNotAvailable() {
        // given
        RoomTypeRepository roomTypeRepo = mock(RoomTypeRepository.class);
        BookingRepository bookingRepo = mock(BookingRepository.class);
        InventoryRepository inventoryRepo = mock(InventoryRepository.class);
        EmailSender emailSender = mock(EmailSender.class);
        HotelService service = spy(new HotelService(roomTypeRepo, bookingRepo, inventoryRepo, emailSender));

        BookingRequest request = new BookingRequest();
        request.roomTypeId = 1L;
        request.from = LocalDate.of(2024, 1, 1);
        request.to = LocalDate.of(2024, 1, 2);
        request.quantity = 1;
        request.amount = new BigDecimal("10.00");

        RoomType roomType = new RoomType();
        roomType.setTotalRooms(1);
        when(roomTypeRepo.findById(1L)).thenReturn(Optional.of(roomType));

        doReturn(new AvailabilityResponse(1L, request.from, request.to, false, 0))
                .when(service).checkAvailability(1L, request.from, request.to, 1);

        // when
        IllegalStateException ex = assertThrows(IllegalStateException.class, () -> service.reserveRoom(request));

        // then
        assertEquals("Not enough rooms available", ex.getMessage());
    }

    @Test
    @DisplayName("createIfMissing saves inventory when missing")
    void createIfMissingSavesInventoryWhenMissing() {
        // given
        RoomTypeRepository roomTypeRepo = mock(RoomTypeRepository.class);
        BookingRepository bookingRepo = mock(BookingRepository.class);
        InventoryRepository inventoryRepo = mock(InventoryRepository.class);
        EmailSender emailSender = mock(EmailSender.class);
        HotelService service = new HotelService(roomTypeRepo, bookingRepo, inventoryRepo, emailSender);

        RoomType roomType = new RoomType();
        roomType.setId(1L);
        when(roomTypeRepo.findById(1L)).thenReturn(Optional.of(roomType));
        when(inventoryRepo.existsByRoomTypeIdAndDate(1L, LocalDate.of(2024, 1, 1))).thenReturn(false);

        ArgumentCaptor<Inventory> captor = ArgumentCaptor.forClass(Inventory.class);

        // when
        service.createIfMissing(1L, LocalDate.of(2024, 1, 1), 5);

        // then
        verify(inventoryRepo).save(captor.capture());
        Inventory saved = captor.getValue();
        assertEquals(roomType, saved.getRoomType());
        assertEquals(LocalDate.of(2024, 1, 1), saved.getDate());
        assertEquals(5, saved.getTotalRooms());
        assertEquals(0, saved.getReservedRooms());
    }

    @Test
    @DisplayName("addReserved increments reserved rooms")
    void addReservedIncrementsReservedRooms() {
        // given
        RoomTypeRepository roomTypeRepo = mock(RoomTypeRepository.class);
        BookingRepository bookingRepo = mock(BookingRepository.class);
        InventoryRepository inventoryRepo = mock(InventoryRepository.class);
        EmailSender emailSender = mock(EmailSender.class);
        HotelService service = new HotelService(roomTypeRepo, bookingRepo, inventoryRepo, emailSender);

        Inventory inventory = new Inventory(new RoomType(), LocalDate.of(2024, 1, 1), 5);
        inventory.setReservedRooms(2);
        when(inventoryRepo.findByRoomTypeIdAndDate(1L, LocalDate.of(2024, 1, 1)))
                .thenReturn(Optional.of(inventory));

        // when
        service.addReserved(1L, LocalDate.of(2024, 1, 1), 3);

        // then
        assertEquals(5, inventory.getReservedRooms());
        verify(inventoryRepo).save(inventory);
    }
}
