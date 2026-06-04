package fr.adriencaubel.hotel.adapter.persistence.mapper;

import fr.adriencaubel.hotel.adapter.persistence.entity.BookingOptionEntity;
import fr.adriencaubel.hotel.domain.BookingOption;
import org.springframework.stereotype.Component;

@Component
public class BookingOptionPersistenceMapper {
    public BookingOption toDomain(BookingOptionEntity bookingOptionEntity) {
        return new BookingOption(bookingOptionEntity.getId(), bookingOptionEntity.getBooking(),
                bookingOptionEntity.getComment(), bookingOptionEntity.getType());
    }

    public BookingOptionEntity toEntity(BookingOption bookingOption) {
        return new BookingOptionEntity(bookingOption.getId(), bookingOption.getBooking(),
                bookingOption.getComment(), bookingOption.getType());
    }
}
