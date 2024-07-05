package az.edu.turing.entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class FlightEntity {
    public static long count = 0;
    private long flightId;
    private Cities origin;
    private Cities destination;
    private int seats;
    private LocalDateTime departureTime;

    public FlightEntity() {
    }

    public FlightEntity(Cities origin, Cities destination, int seats, LocalDateTime departureTime) {
        this.flightId = ++count;
        this.origin = origin;
        this.destination = destination;
        this.seats = seats;
        this.departureTime = departureTime;
    }

    public FlightEntity(long flightId, Cities origin, Cities destination, int seats, LocalDateTime departureTime) {
        this.flightId = flightId;
        this.origin = origin;
        this.destination = destination;
        this.seats = seats;
        this.departureTime = departureTime;
    }

    public long getFlightId() {
        return flightId;
    }

    public Cities getOrigin() {
        return origin;
    }

    public void setOrigin(Cities origin) {
        this.origin = origin;
    }

    public Cities getDestination() {
        return destination;
    }

    public void setDestination(Cities destination) {
        this.destination = destination;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlightEntity that = (FlightEntity) o;
        return flightId == that.flightId && seats == that.seats && Objects.equals(origin, that.origin) && Objects.equals(destination, that.destination) && Objects.equals(departureTime, that.departureTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flightId, origin, destination, seats, departureTime);
    }

    @Override
    public String toString() {
        return "FlightEntity{" +
                "flightId=" + flightId +
                ", origin=" + origin +
                ", destination=" + destination +
                ", seats=" + seats +
                ", departureTime=" + departureTime +
                '}';
    }
}
