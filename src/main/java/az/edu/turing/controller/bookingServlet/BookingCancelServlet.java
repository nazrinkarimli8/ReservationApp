package az.edu.turing.controller.bookingServlet;

import az.edu.turing.service.BookingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class BookingCancelServlet extends HttpServlet {
    private final ObjectMapper objectMapper;
    private final BookingService bookingService;

    public BookingCancelServlet(ObjectMapper objectMapper, BookingService bookingService) {
        this.objectMapper = objectMapper;
        this.bookingService = bookingService;
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            long bookingId = Long.parseLong(req.getParameter("id"));
            bookingService.cancelBooking(bookingId);
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write("Booking cancelled successfully!");
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid id format");
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Something went wrong! Try again!");
        }
    }
}
