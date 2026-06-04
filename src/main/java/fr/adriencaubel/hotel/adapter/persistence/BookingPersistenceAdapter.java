package fr.adriencaubel.hotel.adapter.persistence;

import fr.adriencaubel.hotel.adapter.persistence.mapper.BookingPersistenceMapper;
import fr.adriencaubel.hotel.adapter.persistence.repository.SpringDataBookingRepository;
import fr.adriencaubel.hotel.domain.entity.Booking;
import fr.adriencaubel.hotel.domain.ports.out.repository.BookingRepositoryPort;
import org.springframework.stereotype.Component;

@Component
public class BookingPersistenceAdapter implements BookingRepositoryPort {

    private final BookingPersistenceMapper bookingPersistenceMapper;
    private final SpringDataBookingRepository springDataBookingRepository;

    public BookingPersistenceAdapter(
            BookingPersistenceMapper bookingPersistenceMapper,
            SpringDataBookingRepository springDataBookingRepository) {
        this.bookingPersistenceMapper = bookingPersistenceMapper;
        this.springDataBookingRepository = springDataBookingRepository;
    }

    public void save(Booking author) {
        bookingPersistenceMapper.toDomain(
                springDataBookingRepository.save(bookingPersistenceMapper.toEntity(author))
        );
    }
}
