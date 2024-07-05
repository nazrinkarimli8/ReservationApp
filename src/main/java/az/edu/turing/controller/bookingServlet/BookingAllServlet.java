package az.edu.turing.controller.bookingServlet;

import az.edu.turing.service.BookingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class BookingAllServlet extends HttpServlet {
    private final ObjectMapper objectMapper;
    private final BookingService bookingService;

    public BookingAllServlet(ObjectMapper objectMapper, BookingService bookingService) {
        this.objectMapper = objectMapper;
        this.bookingService = bookingService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String passengerName = req.getParameter("name");
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            if (passengerName != null) {
                objectMapper.writeValue(resp.getWriter(), bookingService.getAllBookingsByPassenger(passengerName));
                resp.setStatus(HttpServletResponse.SC_OK);
            } else {
                objectMapper.writeValue(resp.getWriter(), bookingService.getAllBookings());
                resp.setStatus(HttpServletResponse.SC_OK);
            }
        } catch (IOException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error processing request");
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Something went wrong! Try again!");
        }
    }
}
