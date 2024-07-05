package az.edu.turing.controller.flightServlet;

import az.edu.turing.exception.FlightNotFoundException;
import az.edu.turing.service.FlightService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class FlightCancelServlet extends HttpServlet {
    private final ObjectMapper objectMapper;
    private final FlightService flightService;

    public FlightCancelServlet(ObjectMapper objectMapper, FlightService flightService) {
        this.objectMapper = objectMapper;
        this.flightService = flightService;
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            long flightId = Long.parseLong(req.getParameter("id"));
            flightService.cancelFlight(flightId);
            resp.getWriter().write("Flight cancelled successfully!");
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid id format");
        } catch (FlightNotFoundException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid search parameters");
        }
    }
}