package az.edu.turing.dao;

import az.edu.turing.entity.FlightEntity;

import java.util.List;
import java.util.Optional;

public interface FlightDao {
    void save(FlightEntity entity);

    void cancelFlight(long id);

    List<FlightEntity> findAll();

    List<FlightEntity> findByOrigin(String origin);

    FlightEntity findById(long id);
}
