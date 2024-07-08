package az.edu.turing.dao.impl;

import az.edu.turing.dao.BookingDao;
import az.edu.turing.database.JdbcConnection;
import az.edu.turing.database.SqlQueries;
import az.edu.turing.entity.BookingEntity;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

import static az.edu.turing.database.SqlQueries.updateFlightsQuery;

public class BookingPostgresDao implements BookingDao {

    @Override
    public void save(BookingEntity entity) {
        try (Connection conn = JdbcConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SqlQueries.createBookingQuery, Statement.RETURN_GENERATED_KEYS)
        ) {
            stmt.setLong(1, entity.getFlightId());
            stmt.executeUpdate();
            ResultSet generatedKeys = stmt.getGeneratedKeys();

            if (generatedKeys.next()) {
                long bookingId = generatedKeys.getLong(1);
                for (String passengerName : entity.getPassengerName()) {
                    PreparedStatement stmtPassenger = conn.prepareStatement(SqlQueries.createPassengerNameQuery, Statement.RETURN_GENERATED_KEYS);
                    stmtPassenger.setString(1, passengerName);
                    stmtPassenger.executeUpdate();
                    ResultSet passengerKeys = stmtPassenger.getGeneratedKeys();

                    if (passengerKeys.next()) {
                        long passengerId = generatedKeys.getLong(1);
                        PreparedStatement stmtBookingPassenger = conn.prepareStatement(SqlQueries.createBookingByPassengerIdQuery, Statement.RETURN_GENERATED_KEYS);
                        stmtBookingPassenger.setLong(1, bookingId);
                        stmtBookingPassenger.setLong(2, passengerId);
                        stmtBookingPassenger.executeUpdate();
                    }
                }
                PreparedStatement stmtFlight = conn.prepareStatement(updateFlightsQuery);
                stmtFlight.setLong(1, bookingId);
                stmtFlight.setLong(2, entity.getFlightId());
                stmtFlight.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<BookingEntity> findAll() {
        List<BookingEntity> bookingEntities = new ArrayList<>();
        try (Connection conn = JdbcConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SqlQueries.getAllBookingByIdQuery)) {
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                long flightId = resultSet.getLong("flight_id");
                String passengerName = resultSet.getString("name");
                bookingEntities.add(new BookingEntity(id, flightId, List.of(passengerName.split(","))));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return bookingEntities;
    }

    @Override
    public void cancelBooking(long bookingId) {
        try (Connection conn = JdbcConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(SqlQueries.deleteBookingsPassengersQuery);
            stmt.setLong(1, bookingId);
            stmt.executeUpdate();

            PreparedStatement stmtFlights=conn.prepareStatement(updateFlightsQuery);
            stmtFlights.setLong(1,bookingId);
            stmtFlights.setLong(2,findById(bookingId).getFlightId());
            stmtFlights.executeUpdate();

            PreparedStatement stmtBookings = conn.prepareStatement(SqlQueries.deleteBookingQuery);
            stmtBookings.setLong(1, bookingId);
            stmtBookings.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public BookingEntity findById(long id) {
        BookingEntity bookingEntity = null;
        try (Connection conn = JdbcConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SqlQueries.getBookingByIdQuery)) {
            stmt.setLong(1, id);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                long bookingId = resultSet.getLong("id");
                long flightId = resultSet.getLong("flight_id");
                bookingEntity = new BookingEntity(bookingId, flightId, new ArrayList<>());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return bookingEntity;
    }

    @Override
    public List<BookingEntity> findByName(String passengerNames) {
        List<BookingEntity> bookingEntities = new ArrayList<>();
        try (Connection conn = JdbcConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SqlQueries.getAllBookingByIdQuery)) {
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                long flightId = resultSet.getLong("flight_id");
                String passengerName = resultSet.getString("passenger_name");
                if (passengerNames.contains(passengerName)) {
                    bookingEntities.add(new BookingEntity(id, flightId, List.of(passengerName.split(","))));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return bookingEntities;
    }

    public long getNextBookingId() throws SQLException {
        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "postgres");
             PreparedStatement statement = conn.prepareStatement(SqlQueries.getNextIdSql);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getLong(1);
            } else {
                throw new SQLException("Unable to retrieve next booking ID from sequence.");
            }
        }
    }
}