package fr.adriencaubel.hotel.domain.ports.out.repository;

import fr.adriencaubel.hotel.domain.entity.Booking;

public interface BookingRepositoryPort {
    void save(Booking booking);
}
