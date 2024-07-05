package az.edu.turing.controller.bookingServlet;

import az.edu.turing.entity.BookingEntity;
import az.edu.turing.service.BookingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class BookingByIdServlet extends HttpServlet {
    private final ObjectMapper objectMapper;
    private final BookingService bookingService;

    public BookingByIdServlet(ObjectMapper objectMapper, BookingService bookingService) {
        this.objectMapper = objectMapper;
        this.bookingService = bookingService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            long bookingId = Long.parseLong(req.getParameter("id"));
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            BookingEntity bookingEntity = bookingService.findById(bookingId);
            if (bookingEntity != null) {
                objectMapper.writeValue(resp.getWriter(), bookingEntity);
                resp.setStatus(HttpServletResponse.SC_OK);
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Booking not found!");
            }
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid id format!");
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Something went wrong! Try again!");
        }
    }
}
