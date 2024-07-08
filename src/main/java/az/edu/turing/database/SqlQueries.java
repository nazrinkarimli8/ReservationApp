package az.edu.turing.database;

public class SqlQueries {
    public static final String getBookingByIdQuery = "SELECT * FROM WHERE id=?;";
    public static final String createBookingQuery = "INSERT INTO bookings(flight_id) VALUES(?);";
    public static final String createPassengerNameQuery = "INSERT INTO passengers(name) VALUES(?);";
    public static final String createBookingByPassengerIdQuery = "INSERT INTO booking_passenger (booking_id, passenger_id) VALUES(?,?);";
    public static final String deleteBookingQuery = "DELETE FROM bookings WHERE id=?;";
    public static final String updateFlightsQuery = "UPDATE flights SET seats = seats - (SELECT COUNT(*) FROM booking_passenger WHERE booking_id = ?) WHERE id = ?;";
    public static final String deleteBookingsPassengersQuery = "DELETE FROM booking_passenger WHERE booking_id = ?;";
    public static final String getAllBookingByIdQuery = "SELECT bookings.id, bookings.flight_id, string_agg(passengers.name, ',') as passenger_name FROM bookings JOIN booking_passenger ON bookings.id = booking_passenger.booking_id JOIN passengers ON booking_passenger.passenger_id = passenger.id GROUP BY booking.id;";
    public static final String getNextIdSql = "SELECT nextval('bookings_id_seq')";


    public static final String createFlightQuery = "INSERT INTO flights (origin, destination, departure_time, seats) VALUES (?, ?, ?, ?)";
    public static final String deleteFlightQuery = "DELETE FROM flights WHERE id = ?;";
    public static final String findAllFlightQuery = "SELECT * FROM flights;";
    public static final String findFlightByOriginQuery = "SELECT * FROM flights WHERE origin = ?;";
    public static final String findFlightByIdQuery = "SELECT * FROM flights WHERE id=?;";
    public static final String updateFlightQuery = "UPDATE flights SET " + "origin = ?, " + "destination = ?, " + "departure_time = ?, " + "seats = ? " + "WHERE id = ?";
}

