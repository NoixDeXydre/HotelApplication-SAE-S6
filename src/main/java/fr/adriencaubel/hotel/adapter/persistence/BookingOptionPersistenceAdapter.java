package fr.adriencaubel.hotel.adapter.persistence;

import fr.adriencaubel.hotel.adapter.persistence.mapper.BookingOptionPersistenceMapper;
import fr.adriencaubel.hotel.adapter.persistence.repository.SpringDataBookingOptionRepository;

public class BookingOptionPersistenceAdapter {

    private final BookingOptionPersistenceMapper bookingPersistenceMapper;
    private final SpringDataBookingOptionRepository springDataBookingRepository;

    public BookingOptionPersistenceAdapter(
            BookingOptionPersistenceMapper bookingPersistenceMapper,
            SpringDataBookingOptionRepository springDataBookingRepository) {
        this.bookingPersistenceMapper = bookingPersistenceMapper;
        this.springDataBookingRepository = springDataBookingRepository;
    }
}
