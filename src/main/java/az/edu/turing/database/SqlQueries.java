package az.edu.turing.database;

public class SqlQueries {
    public static final String getBookingByIdQuery = "SELECT * FROM WHERE id=?;";
    public static final String createBookingQuery = "INSERT INTO bookings(flight_id) VALUES(?);";
    public static final String createPassengerNameQuery = "INSERT INTO passengers(full_name) VALUES(?);";
    public static final String createBookingByPassengerIdQuery = "INSERT INTO bookings_passengers (booking_id, passenger_id) VALUES(?,?);";
    public static final String deleteBookingQuery = "DELETE FROM bookings WHERE id=?;";
    public static final String updateFlightsQuery = "UPDATE flights SET free_seats = free_seats - (SELECT COUNT(*) FROM bookings_passengers WHERE booking_id = ?) WHERE id = ?;";
    public static final String deleteBookingsPassengersQuery = "DELETE FROM bookings_passengers WHERE booking_id = ?;";
    public static final String getAllBookingByIdQuery = "SELECT booking.id, booking.flight_id, string_agg(passenger.full_name, ',') as passenger_name FROM booking JOIN bookings_passengers ON booking.id = bookings_passengers.booking_id JOIN passenger ON bookings_passengers.passenger_id = passenger.id GROUP BY booking.id;";
    public static final String getNextIdSql = "SELECT nextval('bookings_id_seq')";


    public static final String createFlightQuery = "INSERT INTO flights (origin, destination, departure_time, seats) VALUES (?, ?, ?, ?)";
    public static final String deleteFlightQuery = "DELETE FROM flights WHERE id = ?;";
    public static final String findAllFlightQuery = "SELECT * FROM flights;";
    public static final String findFlightByOriginQuery = "SELECT * FROM flights WHERE origin = ?;";
    public static final String findFlightByIdQuery = "SELECT * FROM flights WHERE id=?;";
    public static final String updateFlightQuery = "UPDATE flights SET " + "origin = ?, " + "destination = ?, " + "departure_time = ?, " + "seats = ? " + "WHERE id = ?";
}

