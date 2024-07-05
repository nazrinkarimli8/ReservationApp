package az.edu.turing.service;

import az.edu.turing.dao.BookingDao;
import az.edu.turing.dao.FlightDao;
import az.edu.turing.entity.BookingEntity;
import az.edu.turing.model.BookingDto;

import java.util.List;

public interface BookingService {
    void saveBooking(BookingDto bookingDto);

    void cancelBooking(long bookingId);

    List<BookingDto> getAllBookings();

    BookingEntity findById(long bookingId);

    List<BookingDto> getAllBookingsByPassenger(String passengerName);
}