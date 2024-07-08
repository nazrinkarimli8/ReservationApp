package az.edu.turing.service.impl;

import az.edu.turing.dao.FlightDao;
import az.edu.turing.entity.Cities;
import az.edu.turing.entity.FlightEntity;
import az.edu.turing.exception.FlightNotFoundException;
import az.edu.turing.model.FlightDto;
import az.edu.turing.service.FlightService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FlightServiceImpl implements FlightService {
    private final FlightDao flightDao;

    public FlightServiceImpl(FlightDao flightDao) {
        this.flightDao = flightDao;
    }

    @Override
    public void saveFlight(FlightDto flightDto) {
        FlightEntity flightEntity = new FlightEntity(flightDto.getOrigin(),
                flightDto.getDestination(), flightDto.getSeats(),
                flightDto.getDepartureTime());
        flightDao.save(flightEntity);
    }

    @Override
    public void cancelFlight(long flightId) {
        flightDao.cancelFlight(flightId);
    }

    @Override
    public List<FlightDto> getAllFlights() {
        return flightDao.findAll().stream()
                .map(flight -> new FlightDto(flight.getFlightId(), flight.getOrigin(),
                        flight.getDestination(), flight.getSeats(),
                        flight.getDepartureTime()))
                .collect(Collectors.toList());
    }

    @Override
    public FlightDto getFlightById(long flightId) {
        FlightEntity flightEntity = flightDao.findById(flightId);
        if (flightEntity == null) {
            throw new FlightNotFoundException("Flight not found");
        }
        return new FlightDto(flightEntity.getFlightId(), flightEntity.getOrigin(),
                flightEntity.getDestination(), flightEntity.getSeats(),
                flightEntity.getDepartureTime());
    }

    @Override
    public List<FlightDto> getFlightByOrigin(String origin) {
        return flightDao.findByOrigin(origin).stream()
                .map(flight -> new FlightDto(flight.getFlightId(), flight.getOrigin(),
                        flight.getDestination(), flight.getSeats(),
                        flight.getDepartureTime()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<FlightDto> getNext24HoursFlights(Cities origin) {
        try {
            List<FlightEntity> entities = flightDao.findAll();
            return entities.stream().filter(entity ->
                            entity.getOrigin().equals(origin) &&
                                    entity.getDepartureTime().isAfter(LocalDateTime.now()) &&
                                    entity.getDepartureTime().isBefore(LocalDateTime.now().plusHours(24))
                    ).map(entity -> new FlightDto(entity.getFlightId(), entity.getOrigin(),
                            entity.getDestination(), entity.getSeats(),
                            entity.getDepartureTime()))
                    .collect(Collectors.toList());
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public void updateFlight(long id, FlightDto updatedFlightDto) {
        FlightEntity updatedFlightEntity = new FlightEntity(
                id,
                updatedFlightDto.getOrigin(),
                updatedFlightDto.getDestination(),
                updatedFlightDto.getSeats(),
                updatedFlightDto.getDepartureTime()
        );
        flightDao.update(id, updatedFlightEntity);
    }
}