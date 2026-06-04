package fr.adriencaubel.hotel.service;


import fr.adriencaubel.hotel.api.dto.AvailabilityResponse;
import fr.adriencaubel.hotel.api.dto.BookingRequest;
import fr.adriencaubel.hotel.domain.Booking;
import fr.adriencaubel.hotel.domain.BookingOption;
import fr.adriencaubel.hotel.domain.Inventory;
import fr.adriencaubel.hotel.domain.RoomType;
import fr.adriencaubel.hotel.domain.ports.out.notification.IEmailNotification;
import fr.adriencaubel.hotel.infra.BookingRepository;
import fr.adriencaubel.hotel.infra.InventoryRepository;
import fr.adriencaubel.hotel.infra.RoomTypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class HotelService {
    
    private final RoomTypeRepository roomTypeRepo;
    private final BookingRepository bookingRepo;
    private final InventoryRepository inventoryRepo;
    
    private final IEmailNotification emailSender;

    private BookingInvoiceService bookingInvoiceService;
    
    public HotelService(RoomTypeRepository roomTypeRepo,
                        BookingRepository bookingRepo,
                        InventoryRepository inventoryRepo,
                        IEmailNotification emailSender) {
        this.roomTypeRepo = roomTypeRepo;
        this.bookingRepo = bookingRepo;
        this.inventoryRepo = inventoryRepo;
        this.emailSender = emailSender;
        this.bookingInvoiceService = bookingInvoiceService;
    }

    @Transactional(readOnly = true)
    public AvailabilityResponse checkAvailability(Long roomTypeId, LocalDate from, LocalDate to, int qty) {
        if (qty <= 0) qty = 1;
        
        RoomType roomType = roomTypeRepo.findById(roomTypeId).orElseThrow(() -> new IllegalArgumentException("Unknown room type"));

        List<Inventory> inventories =
                inventoryRepo.findByRoomTypeAndDateBetween(roomTypeId, from, to);

        if (inventories.isEmpty()) {
            // Aucun inventaire donc capacité
            return new AvailabilityResponse(
                    roomTypeId,
                    from,
                    to,
                    qty <= roomType.getTotalRooms(),
                    roomType.getTotalRooms()
            );
        }

        // else Compute minimum remaining across the stay
        int minRemaining = inventories.stream()
                .mapToInt(inv -> inv.getTotalRooms() - inv.getReservedRooms())
                .min()
                .orElse(roomType.getTotalRooms());

        boolean available = minRemaining >= qty;

        return new AvailabilityResponse(
                roomTypeId,
                from,
                to,
                available,
                minRemaining
        );
    }
    
    // Volontairement: transaction discutable + mélange paiement/inventory/booking/email
    @Transactional
    public Booking reserveRoom(BookingRequest req) {
        if (req.to.isBefore(req.from)) {
            throw new IllegalArgumentException("to must be after from");
        }

        RoomType rt = roomTypeRepo.findById(req.roomTypeId).orElseThrow
                (() -> new IllegalArgumentException("The room with the id '" + req.roomTypeId + "' couldn't be found"));
        
        AvailabilityResponse check = checkAvailability(req.roomTypeId, req.from, req.to, req.quantity);
        if (!check.available) {
            throw new IllegalStateException("Not enough rooms available");
        }

        // (Optimisation) chargement des inventaires en avance
        List<Inventory> existingInventories =
                inventoryRepo.findByRoomTypeAndDateBetween(req.roomTypeId, req.from, req.to);

        Map<LocalDate, Inventory> inventoryByDate = existingInventories.stream()
                .collect(Collectors.toMap(Inventory::getDate, Function.identity()));

        // On itère sans faire une nouvelle requête à chaque fois.
        List<Inventory> toSave = new ArrayList<>();
        LocalDate d = req.from;
        while (d.isBefore(req.to)) {
            Inventory inv = inventoryByDate.computeIfAbsent(
                    d,
                    date -> new Inventory(rt, date, rt.getTotalRooms()) // FIX 2
            );
            inv.addReservedRooms(req.quantity);
            toSave.add(inv);
            d = d.plusDays(1);
        }

        inventoryRepo.saveAll(toSave); // FIX 1 — un seul appel batch

        CustomerDto customerDto = CustomerValidationService.splitCustomer(req.nomPrenomEmail);
        Booking booking = new Booking(customerDto.getEmail(), customerDto.getFirstName(), customerDto.getLastName(),
                rt, req.from, req.to, req.quantity, req.amount, "CONFIRMED");

        if (req.options != null) {

            for (String rawOption : req.options) {

                if (rawOption == null || rawOption.isBlank()) {
                    continue;
                }

                booking.addOption(new BookingOption(rawOption.split(",", 2)));
            }
        }

        bookingRepo.save(booking);
        bookingInvoiceService.initialInvoice(booking.getId());
        emailSender.sendBookingConfirmation(customerDto.getEmail());
        
        return booking;
    }

    public void createIfMissing(Long roomTypeId, LocalDate date, int totalRooms) {

        boolean exists = inventoryRepo
                .existsByRoomTypeIdAndDate(roomTypeId, date);

        if (!exists) {
            Inventory inventory = new Inventory(roomTypeRepo.findById(roomTypeId).orElse(null),
                    date, totalRooms);
            inventoryRepo.save(inventory);
        }
    }

    public void addReserved(Long roomTypeId, LocalDate date, int quantity) {

        Inventory inventory = inventoryRepo
                .findByRoomTypeIdAndDate(roomTypeId, date)
                .orElseThrow(() -> new IllegalStateException("Inventory missing"));

        inventory.addReservedRooms(quantity);

        inventoryRepo.save(inventory);
    }
}
