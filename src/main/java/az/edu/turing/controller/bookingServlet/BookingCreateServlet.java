package az.edu.turing.controller.bookingServlet;

import az.edu.turing.model.BookingDto;
import az.edu.turing.service.BookingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class BookingCreateServlet extends HttpServlet {
    private final ObjectMapper objectMapper;
    private final BookingService bookingService;

    public BookingCreateServlet(ObjectMapper objectMapper, BookingService bookingService) {
        this.objectMapper = objectMapper;
        this.bookingService = bookingService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            BookingDto bookingDto = objectMapper.readValue(req.getReader(), BookingDto.class);
            bookingService.saveBooking(bookingDto);
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write("Booking successful");
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Something went wrong! Try again!");
        }
    }
}
