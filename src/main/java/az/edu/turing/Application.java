package az.edu.turing;

import az.edu.turing.controller.bookingServlet.BookingAllServlet;
import az.edu.turing.controller.bookingServlet.BookingByIdServlet;
import az.edu.turing.controller.bookingServlet.BookingCancelServlet;
import az.edu.turing.controller.bookingServlet.BookingCreateServlet;
import az.edu.turing.controller.flightServlet.FlightByIdServlet;
import az.edu.turing.controller.flightServlet.FlightCancelServlet;
import az.edu.turing.controller.flightServlet.FlightCreateServlet;
import az.edu.turing.controller.flightServlet.FlightsAllServlet;
import az.edu.turing.dao.BookingDao;
import az.edu.turing.dao.FlightDao;
import az.edu.turing.dao.impl.BookingPostgresDao;
import az.edu.turing.dao.impl.FlightPostgresDao;
import az.edu.turing.service.BookingService;
import az.edu.turing.service.FlightService;
import az.edu.turing.service.impl.BookingServiceImpl;
import az.edu.turing.service.impl.FlightServiceImpl;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Application {
    public static void main(String[] args) throws Exception {
        BookingDao bookingDao = new BookingPostgresDao();
        BookingService bookingService = new BookingServiceImpl(bookingDao);
        FlightDao flightDao = new FlightPostgresDao();
        FlightService flightService = new FlightServiceImpl(flightDao);
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

        Server server = new Server(8080);
        ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        handler.setContextPath("/");
        server.setHandler(handler);

        handler.addServlet(new ServletHolder(new BookingCreateServlet(objectMapper, bookingService)), "/booking/create");
        handler.addServlet(new ServletHolder(new BookingCancelServlet(objectMapper, bookingService)), "/booking/cancel");
        handler.addServlet(new ServletHolder(new BookingAllServlet(objectMapper, bookingService)), "/booking/all");
        handler.addServlet(new ServletHolder(new BookingByIdServlet(objectMapper, bookingService)), "/booking/by-id");

        handler.addServlet(new ServletHolder(new FlightCreateServlet(objectMapper, flightService)), "/flight/create");
        handler.addServlet(new ServletHolder(new FlightCancelServlet(objectMapper, flightService)), "/flight/cancel");
        handler.addServlet(new ServletHolder(new FlightsAllServlet(objectMapper, flightService)), "/flight/all");
        handler.addServlet(new ServletHolder(new FlightByIdServlet(objectMapper, flightService)), "/flight/by-id");

        server.start();
        server.join();
    }
}