package fr.adriencaubel.hotel.adapter.persistence.mapper;

import fr.adriencaubel.hotel.adapter.persistence.entity.BookingEntity;
import fr.adriencaubel.hotel.domain.Booking;
import org.springframework.stereotype.Component;

@Component
public class BookingPersistenceMapper {
    public Booking toDomain(BookingEntity bookingEntity) {
        return new Booking(bookingEntity.getId(), bookingEntity.getRoomType(),
                           bookingEntity.getFromDate(), bookingEntity.getToDate(),
                           bookingEntity.getQuantity(), bookingEntity.getAmount());
    }

    public BookingEntity toEntity(Booking booking) {
        return new BookingEntity(booking.getId(), booking.getRoomType(),
                                 booking.getFromDate(), booking.getToDate(),
                                 booking.getQuantity(), booking.getAmount());
    }
}
