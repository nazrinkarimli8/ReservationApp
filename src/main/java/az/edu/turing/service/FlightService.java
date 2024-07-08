package az.edu.turing.service;

import az.edu.turing.entity.Cities;
import az.edu.turing.model.FlightDto;

import java.util.List;

public interface FlightService {
    void saveFlight(FlightDto flightDto);

    void cancelFlight(long flightId);

    List<FlightDto> getFlightByOrigin(String origin);

    List<FlightDto> getAllFlights();

    FlightDto getFlightById(long id);

    List<FlightDto> getNext24HoursFlights(Cities origin);

    void updateFlight(long id, FlightDto updatedFlightDto);
}
