package az.edu.turing.controller.flightServlet;

import az.edu.turing.exception.FlightNotFoundException;
import az.edu.turing.service.FlightService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class FlightsAllServlet extends HttpServlet {
    private final ObjectMapper objectMapper;
    private final FlightService flightService;

    public FlightsAllServlet(ObjectMapper objectMapper, FlightService flightService) {
        this.objectMapper = objectMapper;
        this.flightService = flightService;
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(objectMapper.writeValueAsString(flightService.getAllFlights()));
        } catch (FlightNotFoundException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid search parameters");
        }
    }
}
