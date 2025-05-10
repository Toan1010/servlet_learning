package servlet;

import dao.FlightDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Flight;

import java.io.IOException;
import java.util.List;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        int page = 1;
        int size = 5;

        try {
            String pageParam = req.getParameter("page");
            if (pageParam != null) {
                page = Integer.parseInt(pageParam);
            }
        } catch (Exception ignored) {
        }


        List<Flight> flights = FlightDao.getListFlight(page, size);
        int totalFlight = FlightDao.countTotalFlights();
        int totalPages = (int) Math.ceil((double) totalFlight / size);

        req.setAttribute("flights", flights);
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages", totalPages);

        req.getRequestDispatcher("/home.jsp").forward(req, resp);
    }
}
