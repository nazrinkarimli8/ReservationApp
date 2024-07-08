package az.edu.turing.dao;

import az.edu.turing.entity.FlightEntity;

import java.util.List;

public interface FlightDao {
    void save(FlightEntity entity);

    void cancelFlight(long id);

    List<FlightEntity> findAll();

    List<FlightEntity> findByOrigin(String origin);

    FlightEntity findById(long id);

    void update(Long id, FlightEntity updatedFlightEntity);
}
