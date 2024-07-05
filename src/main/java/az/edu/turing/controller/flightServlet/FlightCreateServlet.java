package az.edu.turing.controller.flightServlet;

import az.edu.turing.exception.FlightNotFoundException;
import az.edu.turing.model.FlightDto;
import az.edu.turing.service.FlightService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class FlightCreateServlet extends HttpServlet {
    private final ObjectMapper objectMapper;
    private final FlightService flightService;

    public FlightCreateServlet(ObjectMapper objectMapper, FlightService flightService) {
        this.objectMapper = objectMapper;
        this.flightService = flightService;
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            FlightDto flightDto = objectMapper.readValue(req.getReader(), FlightDto.class);
            flightService.saveFlight(flightDto);
            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.getWriter().write("Flight creation successfully!");
        } catch (FlightNotFoundException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }catch(Exception e){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Something went wrong! Try again!");
        }
    }
}
