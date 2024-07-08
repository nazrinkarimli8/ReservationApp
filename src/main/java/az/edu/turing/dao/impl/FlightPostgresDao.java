package az.edu.turing.dao.impl;

import az.edu.turing.dao.FlightDao;
import az.edu.turing.database.JdbcConnection;
import az.edu.turing.database.SqlQueries;
import az.edu.turing.entity.Cities;
import az.edu.turing.entity.FlightEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Timestamp.valueOf;

public class FlightPostgresDao implements FlightDao {

    @Override
    public void save(FlightEntity entity) {
        try (Connection conn = JdbcConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SqlQueries.createFlightQuery)) {
            stmt.setString(1, entity.getOrigin().name());
            stmt.setString(2, entity.getDestination().name());
            stmt.setTimestamp(3, valueOf(entity.getDepartureTime()));
            stmt.setInt(4, entity.getSeats());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void cancelFlight(long id) {
        try (Connection conn = JdbcConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SqlQueries.deleteFlightQuery)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<FlightEntity> findAll() {
        List<FlightEntity> flightEntities = new ArrayList<>();
        try (Connection conn = JdbcConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SqlQueries.findAllFlightQuery)) {
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                FlightEntity flightEntity = new FlightEntity(
                        resultSet.getLong("id"),
                        Cities.valueOf(resultSet.getString("origin")),
                        Cities.valueOf(resultSet.getString("destination")),
                        resultSet.getInt("free_seats"),
                        resultSet.getTimestamp("departure_time").toLocalDateTime()
                );
                flightEntities.add(flightEntity);

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return flightEntities;
    }

    @Override
    public List<FlightEntity> findByOrigin(String origin) {
        List<FlightEntity> flightEntities = new ArrayList<>();
        try (Connection conn = JdbcConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SqlQueries.findFlightByOriginQuery)) {
            stmt.setString(1, origin.toUpperCase());
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                FlightEntity flightEntity = new FlightEntity(
                        resultSet.getLong("id"),
                        Cities.valueOf(resultSet.getString("origin")),
                        Cities.valueOf(resultSet.getString("destination")),
                        resultSet.getInt("free_seats"),
                        resultSet.getTimestamp("departure_time").toLocalDateTime()
                );
                flightEntities.add(flightEntity);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return flightEntities;
    }

    @Override
    public FlightEntity findById(long id) {
        try (Connection conn = JdbcConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SqlQueries.findFlightByIdQuery)) {
            stmt.setLong(1, id);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                FlightEntity flightEntity = new FlightEntity(
                        resultSet.getLong("id"),
                        Cities.valueOf(resultSet.getString("origin")),
                        Cities.valueOf(resultSet.getString("destination")),
                        resultSet.getInt("seats"),
                        resultSet.getTimestamp("date_and_time").toLocalDateTime());
                return flightEntity;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public void update(Long id, FlightEntity updatedFlightEntity) {
        try (Connection conn = JdbcConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SqlQueries.updateFlightQuery)) {
            stmt.setLong(5, id);
            stmt.setString(1, updatedFlightEntity.getOrigin().name());
            stmt.setString(2, updatedFlightEntity.getDestination().name());
            stmt.setTimestamp(3, Timestamp.valueOf(updatedFlightEntity.getDepartureTime()));
            stmt.setInt(4, updatedFlightEntity.getSeats());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
