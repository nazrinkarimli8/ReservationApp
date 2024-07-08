package az.edu.turing.dao;

import az.edu.turing.entity.BookingEntity;

import java.util.List;

public interface BookingDao {
    void save(BookingEntity entity);

    void cancelBooking(long bookingId);

    List<BookingEntity> findAll();

    BookingEntity findById(long id);

    List<BookingEntity> findByName(String passengerName);
}
