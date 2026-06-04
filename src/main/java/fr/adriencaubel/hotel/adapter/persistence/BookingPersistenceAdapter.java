package fr.adriencaubel.hotel.adapter.persistence;

import fr.adriencaubel.hotel.adapter.persistence.mapper.BookingPersistenceMapper;
import fr.adriencaubel.hotel.adapter.persistence.repository.SpringDataBookingRepository;
import fr.adriencaubel.hotel.domain.Booking;
import org.springframework.stereotype.Component;

@Component
public class BookingPersistenceAdapter {

    private final BookingPersistenceMapper bookingPersistenceMapper;
    private final SpringDataBookingRepository springDataBookingRepository;

    public BookingPersistenceAdapter(
            BookingPersistenceMapper bookingPersistenceMapper,
            SpringDataBookingRepository springDataBookingRepository) {
        this.bookingPersistenceMapper = bookingPersistenceMapper;
        this.springDataBookingRepository = springDataBookingRepository;
    }

    public Booking save(Booking author) {
        return bookingPersistenceMapper.toDomain(
                springDataBookingRepository.save(bookingPersistenceMapper.toEntity(author))
        );
    }
}
