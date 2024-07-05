package az.edu.turing.service.impl;

import az.edu.turing.dao.BookingDao;
import az.edu.turing.entity.BookingEntity;
import az.edu.turing.model.BookingDto;
import az.edu.turing.service.BookingService;

import java.util.List;
import java.util.stream.Collectors;

public class BookingServiceImpl implements BookingService {

    private final BookingDao bookingDao;

    public BookingServiceImpl(BookingDao bookingDao) {
        this.bookingDao = bookingDao;
    }

    @Override
    public void saveBooking(BookingDto bookingDto) {
        BookingEntity bookingEntity = new BookingEntity(bookingDto.getFlightId(), bookingDto.getPassengerName());
        bookingDao.save(bookingEntity);
        bookingDto.setTicketId(bookingEntity.getTicketId()); // Ensure ID is set in BookingDto
    }

    @Override
    public void cancelBooking(long bookingId) {
        bookingDao.cancelBooking(bookingId);
    }

    @Override
    public List<BookingDto> getAllBookings() {
        return bookingDao.findAll().stream()
                .map(booking -> new BookingDto(booking.getTicketId(), booking.getFlightId(), booking.getPassengerName()))
                .collect(Collectors.toList());
    }

    @Override
    public BookingEntity findById(long bookingId) {
        return bookingDao.findById(bookingId);
    }

    @Override
    public List<BookingDto> getAllBookingsByPassenger(String passengerName) {
        return bookingDao.findByName(passengerName).stream()
                .map(booking -> new BookingDto(booking.getTicketId(), booking.getFlightId(), booking.getPassengerName()))
                .collect(Collectors.toList());
    }
}