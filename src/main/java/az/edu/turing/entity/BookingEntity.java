package az.edu.turing.entity;

import java.util.List;
import java.util.Objects;

public class BookingEntity {
    public static long count = 0;
    private long ticketId;
    private long flightId;
    private List<String> passengerName;

    public BookingEntity() {
    }

    public BookingEntity(long flightId, List<String> passengerName) {
        this.ticketId = ++count;
        this.flightId = flightId;
        this.passengerName = passengerName;
    }

    public BookingEntity(long ticketId, long flightId, List<String> passengerName) {
        this.ticketId = ticketId;
        this.flightId = flightId;
        this.passengerName = passengerName;
    }


    public long getTicketId() {
        return ticketId;
    }

    public void setTicketId(long ticketId) {
        this.ticketId = ticketId;
    }

    public long getFlightId() {
        return flightId;
    }

    public void setFlightId(long flightId) {
        this.flightId = flightId;
    }

    public List<String> getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(List<String> passengerName) {
        this.passengerName = passengerName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookingEntity that = (BookingEntity) o;
        return ticketId == that.ticketId && flightId == that.flightId && Objects.equals(passengerName, that.passengerName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticketId, flightId, passengerName);
    }

    @Override
    public String toString() {
        return "BookingEntity{" +
                "ticketId=" + ticketId +
                ", flightId=" + flightId +
                ", passengerName=" + passengerName +
                '}';
    }
}
